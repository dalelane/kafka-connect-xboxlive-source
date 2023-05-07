package uk.co.dalelane.kafkaconnect.xboxlive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A source connector for making data from Xbox Live available
 *  as Kafka events.
 */
public class XblSourceConnector extends SourceConnector {

    protected static final String VERSION = "0.1.2";

    private final Logger log = LoggerFactory.getLogger(XblSourceConnector.class);

    private XblConfig config;


    @Override
    public ConfigDef config() {
        return XblConfig.configDef();
    }


    @Override
    public Class<? extends Task> taskClass() {
        return XblSourceTask.class;
    }


    /**
     * Checks that all required config values are provided, throws
     *  a descriptive exception if any are found to be missing.
     */
    @Override
    public Config validate(Map<String, String> connectorConfigs) {
        log.info("Validating config {}", connectorConfigs);

        Config validatedConfigs = super.validate(connectorConfigs);

        boolean missingApiKey = true;
        boolean missingTopicPrefix = true;
        boolean missingPollInterval = true;

        // check all of the provided config values before declaring
        //  anything missing
        for (ConfigValue configValue : validatedConfigs.configValues()) {
            if (configValue.name().equals(XblConfig.API_KEY_PARAM_CONFIG)) {
                missingApiKey = false;
            }
            else if (configValue.name().equals(XblConfig.TOPIC_NAME_PREFIX_PARAM_CONFIG)) {
                missingTopicPrefix = false;
            }
            else if (configValue.name().equals(XblConfig.POLL_INTERVAL_PARAM_CONFIG)) {
                missingPollInterval = false;
            }
        }
        if (missingApiKey) {
            throw new ConnectException("API key for OpenXbl is required (xbl.api.key)");
        }
        if (missingTopicPrefix) {
            throw new ConnectException("Topic name is required (topic.prefix)");
        }
        if (missingPollInterval) {
            throw new ConnectException("OpenXbl API poll interval is required (xbl.api.poll.interval)");
        }

        return validatedConfigs;
    }

    /**
     * Fetch a set of config for connector tasks.
     */
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        if (maxTasks > 1) {
            log.warn("Only one task is supported. Ignoring tasks.max which is set to {}", maxTasks);
        }

        List<Map<String, String>> taskConfigs = new ArrayList<>(1);
        taskConfigs.add(config.getTaskConfig());
        return taskConfigs;
    }


    /**
     * Starts the connector - initialise the connector config.
     */
    @Override
    public void start(Map<String, String> props) {
        log.info("Starting connector {}", props);
        config = new XblConfig(props);
    }


    @Override
    public void stop() {
        log.info("Stopping connector");
    }


    @Override
    public String version() {
        return VERSION;
    }
}
