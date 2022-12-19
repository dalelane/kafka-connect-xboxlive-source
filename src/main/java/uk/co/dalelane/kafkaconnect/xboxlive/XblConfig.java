package uk.co.dalelane.kafkaconnect.xboxlive;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;


public class XblConfig extends AbstractConfig {

    public static final String TOPIC_NAME_PREFIX_PARAM_CONFIG = "topic.prefix";
    public static final String TOPIC_NAME_PREFIX_PARAM_DOC = "Prefix to use for topics to produce to";

    public static final String API_KEY_PARAM_CONFIG = "xbl.api.key";
    public static final String API_KEY_PARAM_DOC = "API key for OpenXBL (https://xbl.io)";

    public static final String POLL_INTERVAL_PARAM_CONFIG = "xbl.api.poll.interval";
    public static final String POLL_INTERVAL_PARAM_DOC = "How frequently the Connector should call the Xbox Live APIs (in seconds)";


    public XblConfig(final Map<?, ?> originals) {
        super(configDef(), originals);
    }


    public static ConfigDef configDef() {
        return new ConfigDef()
            .define(
        		TOPIC_NAME_PREFIX_PARAM_CONFIG,
                Type.STRING,
                Importance.HIGH,
                TOPIC_NAME_PREFIX_PARAM_DOC)
            .define(
                API_KEY_PARAM_CONFIG,
                Type.STRING,
                Importance.HIGH,
                API_KEY_PARAM_DOC)
           .define(
        		POLL_INTERVAL_PARAM_CONFIG,
        		Type.INT,
        		Importance.HIGH,
        		POLL_INTERVAL_PARAM_DOC);
    }


    public Map<String, String> getTaskConfig() {
        Map<String, String> taskConfig = new HashMap<>();

        taskConfig.put(
            XblConfig.TOPIC_NAME_PREFIX_PARAM_CONFIG,
            getTopicPrefix());
        taskConfig.put(
            XblConfig.API_KEY_PARAM_CONFIG,
            getApiKey());
        taskConfig.put(
        	XblConfig.POLL_INTERVAL_PARAM_CONFIG,
        	Integer.toString(getPollInterval()));

        return taskConfig;
    }


    public String getTopicPrefix() {
        return this.getString(TOPIC_NAME_PREFIX_PARAM_CONFIG);
    }

    public String getApiKey() {
        return this.getString(API_KEY_PARAM_CONFIG);
    }

    public int getPollInterval() {
    	return this.getInt(POLL_INTERVAL_PARAM_CONFIG);
    }
}
