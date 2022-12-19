package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;


public class PresenceFetcherTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(PresenceFetcherTask.class);

    private final PresenceCache dataCache;
    private final XblConfig connectorConfig;

    private URL urlObj;
    private Type collectionType;
    private Gson parser;


    public PresenceFetcherTask(PresenceCache cache, XblConfig config) {
        log.info("Initializing presence fetcher task");

        dataCache = cache;
        connectorConfig = config;

        try {
			urlObj = new URL("https://xbl.io/api/v2/presence");
		}
        catch (MalformedURLException e) {
        	log.error("failed to create URL", e);
		}

        collectionType = TypeToken.getParameterized(List.class, Presence.class).getType();
        parser = new Gson();


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
			URLConnection conn = urlObj.openConnection();
			conn.setRequestProperty("x-authorization", connectorConfig.getApiKey());

			InputStream is = conn.getInputStream();

			Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

	        return parser.fromJson(reader, collectionType);
    	}
    	catch (IOException e) {
			log.error("Failed to fetch presence data", e);
			return Collections.emptyList();
    	}
    }
}
