package uk.co.dalelane.kafkaconnect.xboxlive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A source connector for making data from Xbox Live available
 *  as Kafka events.
 */
public class XblSourceConnector extends SourceConnector {

    protected static final String VERSION = "0.1.4";

    private final Logger log = LoggerFactory.getLogger(XblSourceConnector.class);

    private Map<String, String> configProps = null;


    @Override
    public ConfigDef config() {
        return XblConfig.CONFIG_DEF;
    }


    @Override
    public Class<? extends Task> taskClass() {
        return XblSourceTask.class;
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
        taskConfigs.add(configProps);
        return taskConfigs;
    }


    /**
     * Starts the connector - initialise the connector config.
     */
    @Override
    public void start(Map<String, String> props) {
        log.info("Starting connector {}", props);
        configProps = props;
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
