package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.records.SourceRecordFactory;


/**
 * Manages scheduled tasks that will poll Xbox Live APIs at regular intervals
 *  and store the parsed responses in a local in-memory cache.
 */
public class DataMonitor {

    private static Logger log = LoggerFactory.getLogger(DataMonitor.class);

    // flag if the scheduled tasks should currently be running
    private boolean isRunning;

    // how frequently the APIs should be polled
    private final int pollIntervalMs;

    // timer managing when the APIs should be polled
    private Timer fetcherTimer;

    // fetcher for activity responses (e.g. achievements)
    private final ActivityFetcherTask activityFetcher;
    private final ActivityItemCache activityData;

    // fetcher for presence responses (e.g. online/offline status)
    private final PresenceFetcherTask presenceFetcher;
    private final PresenceCache presenceData;

    // record factory for turning Xbox Live API responses into
    //   Kafka Connect records
    private final SourceRecordFactory recordFactory;



    public DataMonitor(XblConfig config, Instant startTimestamp) {
        log.info("Creating monitor {} {}", config, startTimestamp);

        isRunning = false;

        pollIntervalMs = config.getPollInterval() * 1000;

        recordFactory = new SourceRecordFactory(config);

        activityData = new ActivityItemCache(startTimestamp);
        activityFetcher = new ActivityFetcherTask(activityData, config);

        presenceData = new PresenceCache();
        presenceFetcher = new PresenceFetcherTask(presenceData, config);
    }


    public synchronized void start() {
        log.info("Starting monitor");

        if (isRunning == false) {
            // a single timer is used for both tasks
            fetcherTimer = new Timer();
            fetcherTimer.scheduleAtFixedRate(activityFetcher, 0, pollIntervalMs);
            fetcherTimer.scheduleAtFixedRate(presenceFetcher, 0, pollIntervalMs);

            isRunning = true;
        }
    }


    public synchronized void stop() {
        log.info("Stopping monitor");

        if (isRunning) {
            fetcherTimer.cancel();

            isRunning = false;
        }
    }


    public List<SourceRecord> getRecords() {
        Stream<SourceRecord> activityRecords = activityData.getActivityItems()
                .stream()
                .map(d -> recordFactory.createSourceRecord(d));
        Stream<SourceRecord> presenceRecords = presenceData.getPresences()
                .stream()
                .map(d -> recordFactory.createSourceRecord(d));
        return Stream.concat(activityRecords, presenceRecords)
                .collect(Collectors.toList());
    }
}
