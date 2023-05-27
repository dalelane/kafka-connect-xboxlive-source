package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;

import org.apache.kafka.common.config.AbstractConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;

/**
 * Fetches data from the Xbox Live API at scheduled intervals.
 */
public abstract class XboxTimerTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(XboxTimerTask.class);

    // config - needed to provide access to the API key
    private final AbstractConfig connectorConfig;

    // URL that will be polled when the task is run
    private URL urlObj;

    // custom JSON parser for parsing the data read from
    //  the Xbox Live API into Java POJOs
    protected Gson parser;


    public XboxTimerTask(String url, AbstractConfig config) {
        log.info("Initializing xbox api fetcher task for {}", url);

        connectorConfig = config;

        // check that a valid URL has been provided
        try {
            urlObj = URI.create(url).toURL();
        }
        catch (MalformedURLException e) {
            log.error("failed to create URL", e);
        }

        // create the parser that will be used for
        //  API responses fetched by this task
        parser = createParser();
    }


    /**
     * Create a parser for the data we expect to retrieve from the API
     */
    protected abstract Gson createParser();


    protected Reader getApiReader() throws IOException {
        // add request header with the API key required by xbl.io
        URLConnection conn = urlObj.openConnection();
        conn.setRequestProperty("x-authorization", connectorConfig.getString(XblConfig.API_KEY_PARAM_CONFIG));

        return new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
    }
}
