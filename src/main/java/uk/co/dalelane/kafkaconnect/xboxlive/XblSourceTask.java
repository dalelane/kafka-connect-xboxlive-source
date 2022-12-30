package uk.co.dalelane.kafkaconnect.xboxlive;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemTypes;
import uk.co.dalelane.kafkaconnect.xboxlive.fetcher.DataMonitor;
import uk.co.dalelane.kafkaconnect.xboxlive.records.SourceRecordFactory;


public class XblSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(XblSourceTask.class);

    private DataMonitor sourceData = null;

    @Override
    public void start(Map<String, String> properties) {
        log.info("Starting task {}", properties);

        if (sourceData == null) {
            XblConfig config = new XblConfig(properties);
            sourceData = new DataMonitor(config, getPersistedOffset(config));
        }

        sourceData.start();
    }


    @Override
    public void stop() {
        log.info("Stopping task");
        if (sourceData != null) {
            sourceData.stop();
        }
    }


    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return sourceData.getRecords();
    }


    @Override
    public String version() {
        return "0.0.2";
    }



    private Instant getPersistedOffset(XblConfig config) {
    	log.debug("getting persisted offset");

    	Instant offsetTimestamp = Instant.MIN;

    	if (context != null && context.offsetStorageReader() != null) {
    		ActivityItemTypes[] activityItemTypes = new ActivityItemTypes[] {
    			ActivityItemTypes.ACHIEVEMENT,
    			ActivityItemTypes.CONTAINER,
    			ActivityItemTypes.GAME_DVR,
    			ActivityItemTypes.SCREENSHOT,
    			ActivityItemTypes.TEXT_POST,
    			ActivityItemTypes.USER_POST,
    		};

    		for (ActivityItemTypes activityItemType : activityItemTypes) {
    			log.debug("looking for offset for " + activityItemType);
    			Map<String, Object> sourcePartition = SourceRecordFactory.createSourcePartition(activityItemType);
    			Map<String, Object> persistedOffsetInfo = context.offsetStorageReader().offset(sourcePartition);
    			if (persistedOffsetInfo != null) {
    				String lastOffsetTimestamp = (String) persistedOffsetInfo.get(SourceRecordFactory.SOURCE_OFFSET);
    				Instant lastOffsetTime = Instant.parse(lastOffsetTimestamp);

    				log.debug("offset for " + activityItemType + " is " + lastOffsetTime.toString());

    				if (lastOffsetTime.compareTo(offsetTimestamp) > 0) {
    					offsetTimestamp = lastOffsetTime;
    				}
     			}
    			else {
    				log.debug("no persisted offset found");
    			}
    		}
    	}

    	log.info("offset timestamp {}", offsetTimestamp);

    	return offsetTimestamp;
    }
}
