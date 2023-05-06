package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.InstantDeserializer;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;


/**
 * Fetches presence (i.e. are users currently online or offline) from
 *  the Xbox Live API at scheduled intervals, and adds it to a local
 *  cache.
 */
public class PresenceFetcherTask extends XboxTimerTask {

    private static Logger log = LoggerFactory.getLogger(PresenceFetcherTask.class);

    /**
     * API used by this task.
     *
     *  Documentation for this API can be found at https://xbl.io/console
     */
    private static final String API_URL = "https://xbl.io/api/v2/presence";


    /**
     * items retrieved from the Xbox Live API will be added to this cache
     */
    private final PresenceCache dataCache;


    /** the type we expect to be able to parse API responses to */
    private Type collectionType = TypeToken.getParameterized(List.class, Presence.class).getType();


    public PresenceFetcherTask(PresenceCache cache, XblConfig config) {
        super(API_URL, config);
        log.debug("Created PresenceFetcherTask using {}", API_URL);

        // store a reference to the cache to add items to
        dataCache = cache;
    }


    @Override
    public void run() {
        log.info("Fetching presence data from API");

        try {
            // get API response
            Reader reader = getApiReader();

            // parse API response into Java POJO's
            Collection<Presence> presences = parser.fromJson(reader, collectionType);

            // add the parsed response to the cache
            dataCache.addPresences(presences);
        }
        catch (IOException e) {
            log.error("Failed to fetch presence data", e);
        }
    }


    /**
     * Create custom JSON parser that can create Java objects representing
     *  Xbox presence events.
     */
    @Override
    protected Gson createParser() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        return gsonBuilder.create();
    }
}
