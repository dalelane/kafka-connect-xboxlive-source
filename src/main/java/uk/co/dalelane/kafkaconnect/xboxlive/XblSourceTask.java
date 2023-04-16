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


/**
 * Worker task for the Xbox connector - manages the
 *  lifecycle of the threads which fetch data from the
 *  Xbox Live APIs.
 */
public class XblSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(XblSourceTask.class);

    private DataMonitor sourceData = null;

    @Override
    public void start(Map<String, String> properties) {
        log.info("Starting task {}", properties);

        // if this is the first time the task is being started,
        //  set up the background thread that will fetch data
        // otherwise, re-start an existing worker
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
        return XblSourceConnector.VERSION;
    }


    /**
     * Retrieves the timestamp of the last achievement emitted by
     *  a previous running instance of this connector.
     *
     * This will be used to avoid emitting duplicate events when the
     *  Connector restarts - any achievements retrieved from the
     *  Xbox API with timestamps that are equal or earlier to this
     *  will be ignored.
     */
    private Instant getPersistedOffset(XblConfig config) {
        log.debug("retrieving offset for previously produced achievement events");

        Instant offsetTimestamp = Instant.MIN;

        if (context == null || context.offsetStorageReader() != null) {
            log.debug("No context - assuming that this is the first time the Connector has run");
        }
        else {
            ActivityItemTypes[] activityItemTypes = new ActivityItemTypes[] {
                ActivityItemTypes.ACHIEVEMENT,
                ActivityItemTypes.CONTAINER,
                ActivityItemTypes.GAME_DVR,
                ActivityItemTypes.SCREENSHOT,
                ActivityItemTypes.TEXT_POST,
                ActivityItemTypes.USER_POST,
            };

            for (ActivityItemTypes activityItemType : activityItemTypes) {
                Instant lastOffsetTime = getTimestampFromPersistedOffset(activityItemType);
                if (lastOffsetTime != null) {
                    log.debug("offset for " + activityItemType + " is " + lastOffsetTime.toString());

                    // is it later than achievements emitted for other activity types?
                    if (lastOffsetTime.compareTo(offsetTimestamp) > 0) {
                        offsetTimestamp = lastOffsetTime;
                    }
                }
                else {
                    log.debug("no persisted timestamp found for {}", activityItemType.name());
                }
            }
        }

        // this is the timestamp we will use to filter out events
        //  retrieved from the Xbox Live API
        log.info("offset timestamp {}", offsetTimestamp);
        return offsetTimestamp;
    }



    private Instant getTimestampFromPersistedOffset(ActivityItemTypes activityItemType) {
        log.debug("getting timestamp for last event of type {}", activityItemType.name());
        Map<String, Object> sourcePartition = SourceRecordFactory.createSourcePartition(activityItemType);
        Map<String, Object> persistedOffsetInfo = context.offsetStorageReader().offset(sourcePartition);

        if (persistedOffsetInfo != null) {
            String lastOffsetTimestamp = (String) persistedOffsetInfo.get(SourceRecordFactory.SOURCE_OFFSET);
            return Instant.parse(lastOffsetTimestamp);
        }
        else {
            return null;
        }
    }
}
