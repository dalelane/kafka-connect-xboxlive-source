package uk.co.dalelane.kafkaconnect.xboxlive;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;


/**
 * Defines the config that the connector expects Kafka Connect to provide.
 */
public class XblConfig {

    public static final String TOPIC_NAME_PREFIX_PARAM_CONFIG = "topic.prefix";
    private static final String TOPIC_NAME_PREFIX_PARAM_DOC = "Prefix to use for topics to produce to";

    public static final String API_KEY_PARAM_CONFIG = "xbl.api.key";
    private static final String API_KEY_PARAM_DOC = "API key for OpenXBL (https://xbl.io)";

    public static final String POLL_INTERVAL_PARAM_CONFIG = "xbl.api.poll.interval";
    private static final String POLL_INTERVAL_PARAM_DOC = "How frequently the Connector should call the Xbox Live APIs (in seconds)";


    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(TOPIC_NAME_PREFIX_PARAM_CONFIG,
                Type.STRING,
                "",
                Importance.HIGH,
                TOPIC_NAME_PREFIX_PARAM_DOC)
            .define(API_KEY_PARAM_CONFIG,
                Type.STRING,
                "",
                new ConfigDef.NonEmptyString(),
                Importance.HIGH,
                API_KEY_PARAM_DOC)
           .define(POLL_INTERVAL_PARAM_CONFIG,
                Type.INT,
                60, // 1 minute
                ConfigDef.Range.atLeast(1),
                Importance.HIGH,
                POLL_INTERVAL_PARAM_DOC);
}
