package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityFeed;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItem;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemDeserializer;


/**
 * Fetches activity events (e.g. achievements) from the Xbox Live API
 *  at scheduled intervals, and adds it to a local cache.
 */
public class ActivityFetcherTask extends XboxTimerTask {

    private static Logger log = LoggerFactory.getLogger(ActivityFetcherTask.class);

    /**
     * API used by this task.
     *
     *  Documentation for this API can be found at https://xbl.io/console
     */
    private static final String API_URL = "https://xbl.io/api/v2/activity/feed";


    /**
     * items retrieved from the Xbox Live API will be added to this cache
     */
    private final ActivityItemCache dataCache;


    public ActivityFetcherTask(ActivityItemCache cache, XblConfig config) {
        super(API_URL, config);
        log.debug("Created ActivityFetcherTask using {}", API_URL);

        // store a reference to the cache to add items to
        dataCache = cache;
    }


    @Override
    public void run() {
        log.info("Fetching activity data from API");

        try {
            // get API response
            Reader reader = getApiReader();

            // parse API response into Java POJO's
            ActivityFeed ad = parser.fromJson(reader, ActivityFeed.class);

            // add the parsed response to the cache
            dataCache.addFeed(ad);
        }
        catch (IOException e) {
            log.error("Failed to fetch activity data", e);
        }
    }


    /**
     * Create custom JSON parser that can create Java objects representing
     *  Xbox Activity items.
     */
    @Override
    protected Gson createParser() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ActivityItem.class,
                                        new ActivityItemDeserializer());
        return gsonBuilder.create();
    }
}
