package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.records.ActivityRecordFactory;
import uk.co.dalelane.kafkaconnect.xboxlive.records.PresenceRecordFactory;


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

    // record factories for turning Xbox Live API responses into
    //   Kafka Connect records
    private final ActivityRecordFactory activityRecordFactory;
    private final PresenceRecordFactory presenceRecordFactory;



    public DataMonitor(AbstractConfig config, Instant startActivityItems, Instant startPresences) {
        log.info("Creating monitor {} {}", config, startActivityItems, startPresences);

        isRunning = false;

        pollIntervalMs = config.getInt(XblConfig.POLL_INTERVAL_PARAM_CONFIG) * 1000;

        // ACTIVITY ITEMS - e.g. Achievements
        //
        // class for creating Kafka Connect records
        activityRecordFactory = new ActivityRecordFactory(config);
        // cache for holding API responses waiting to be turned into Kafka Connect records
        activityData = new ActivityItemCache(startActivityItems);
        // worker for fetching API responses and putting them in the cache
        activityFetcher = new ActivityFetcherTask(activityData, config);

        // PRESENCE - e.g. online/offline events
        //
        // class for creating Kafka Connect records
        presenceRecordFactory = new PresenceRecordFactory(config);
        // cache for holding API responses waiting to be turned into Kafka Connect records
        presenceData = new PresenceCache(startPresences);
        // worker for fetching API responses and putting them in the cache
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
        // combine the events from both caches into a single list
        //  ready for returning to Kafka Connect
        return Stream
            .concat(
                //
                // activity data - e.g. Achievements
                //
                activityData.getActivityItems()
                    .stream()
                    // turn the Xbox API response into a Connect record
                    .map(d -> activityRecordFactory.createSourceRecord(d))
                    // filter out any API responses that could not be parsed
                    .filter(rec -> rec != null),

                //
                // presence data - e.g. start/stop playing a game
                //
                presenceData.getPresences()
                    .stream()
                    // turn the Xbox API response into a Connect record
                    .map(d -> presenceRecordFactory.createSourceRecord(d))
                    // filter out any API responses that could not be parsed
                    .filter(rec -> rec != null))

            .collect(Collectors.toList());
    }
}
