package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;


/**
 * Fetches presence (i.e. are users currently online or offline) from
 *  the Xbox Live API at scheduled intervals, and adds it to a local
 *  cache.
 */
public class PresenceFetcherTask extends XboxTimerTask {

    private static Logger log = LoggerFactory.getLogger(PresenceFetcherTask.class);

    // items retrieved from the Xbox Live API will be
    //  added to this cache
    private final PresenceCache dataCache;

    // the type we expect to be able to parse API responses to
    private Type collectionType = TypeToken.getParameterized(List.class, Presence.class).getType();


    public PresenceFetcherTask(PresenceCache cache, XblConfig config) {
        super("https://xbl.io/api/v2/presence", config);

        // store a reference to the cache to add items to
        dataCache = cache;

        // seed the cache with an initial response from the API
        Collection<Presence> initialPresences = getPresenceData();
        dataCache.init(initialPresences);
    }


    @Override
    public void run() {
        log.info("Fetching presence data from API");

        Collection<Presence> presences = getPresenceData();
        dataCache.addPresences(presences);
    }


    private Collection<Presence> getPresenceData() {
        try {
            // get API response
            Reader reader = getApiReader();

            // parse API response into Java POJO's
            return parser.fromJson(reader, collectionType);
        }
        catch (IOException e) {
            log.error("Failed to fetch presence data", e);
            return Collections.emptyList();
        }
    }


    @Override
    protected Gson createParser() {
        return new Gson();
    }
}
