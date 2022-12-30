package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityFeed;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItem;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemDeserializer;


public class ActivityFetcherTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(ActivityFetcherTask.class);

    private final ActivityItemCache dataCache;
    private final XblConfig connectorConfig;

    private URL urlObj;
    private Gson parser;



    public ActivityFetcherTask(ActivityItemCache cache, XblConfig config) {
        log.info("Initializing activity fetcher task");

        dataCache = cache;
        connectorConfig = config;

        try {
            urlObj = new URL("https://xbl.io/api/v2/activity/feed");
        }
        catch (MalformedURLException e) {
            log.error("failed to create URL", e);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ActivityItem.class, new ActivityItemDeserializer());
        parser = gsonBuilder.create();
    }

    @Override
    public void run() {
        log.info("Fetching activity data from API");

        try {
            URLConnection conn = urlObj.openConnection();
            conn.setRequestProperty("x-authorization", connectorConfig.getApiKey());

            InputStream is = conn.getInputStream();

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

            ActivityFeed ad = parser.fromJson(reader, ActivityFeed.class);
            dataCache.addFeed(ad);
        }
        catch (IOException e) {
            log.error("Failed to fetch activity data", e);
        }
    }
}
