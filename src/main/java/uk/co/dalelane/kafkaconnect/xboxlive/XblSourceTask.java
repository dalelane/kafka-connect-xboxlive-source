package uk.co.dalelane.kafkaconnect.xboxlive;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.connect.storage.OffsetStorageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.fetcher.DataMonitor;
import uk.co.dalelane.kafkaconnect.xboxlive.records.ActivityRecordFactory;
import uk.co.dalelane.kafkaconnect.xboxlive.records.PresenceRecordFactory;


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
            log.debug("reading config from task properties");
            AbstractConfig config = new AbstractConfig(XblConfig.CONFIG_DEF, properties);

            OffsetStorageReader offsetStorageReader = getOffsetStorageReader();
            sourceData = new DataMonitor(config,
                ActivityRecordFactory.getPersistedOffset(offsetStorageReader),
                PresenceRecordFactory.getPersistedOffset(offsetStorageReader));
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


    private OffsetStorageReader getOffsetStorageReader () {
        if (context == null) {
            log.debug("No context - assuming that this is the first time the Connector has run");
            return null;
        }
        else if (context.offsetStorageReader() == null) {
            log.debug("No offset reader - assuming that this is the first time the Connector has run");
            return null;
        }
        else {
            return context.offsetStorageReader();
        }
    }
}
