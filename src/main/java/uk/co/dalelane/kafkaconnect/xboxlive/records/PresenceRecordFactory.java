package uk.co.dalelane.kafkaconnect.xboxlive.records;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.storage.OffsetStorageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Title;

public class PresenceRecordFactory {

    private static Logger log = LoggerFactory.getLogger(PresenceRecordFactory.class);

    public static final String SOURCE_PARTITION = "presence";

    /** which field in an ActivityItem to use to represent the source offset */
    public static final String SOURCE_OFFSET    = "date";

    private final String presenceTopic;

    public PresenceRecordFactory(XblConfig config) {
        presenceTopic = config.getTopicPrefix() + "PRESENCE";
    }

    private static final Schema PRESENCE_SCHEMA = SchemaBuilder.struct()
        .name("presence")
            .field("date", Schema.STRING_SCHEMA)
            .field("userid", Schema.STRING_SCHEMA)
            .field("state", Schema.STRING_SCHEMA)
            .field("titleid", Schema.OPTIONAL_STRING_SCHEMA)
            .field("titlename", Schema.OPTIONAL_STRING_SCHEMA)
        .build();

    private static Struct createStruct(Presence data) {
        Struct struct = new Struct(PRESENCE_SCHEMA);

        struct.put(PRESENCE_SCHEMA.field("date"), data.getDate().toString());

        struct.put(PRESENCE_SCHEMA.field("userid"), data.getXuid());
        struct.put(PRESENCE_SCHEMA.field("state"), data.getState());

        Title title = data.getFullTitle();
        if (title != null) {
            struct.put(PRESENCE_SCHEMA.field("titleid"), title.getId());
            struct.put(PRESENCE_SCHEMA.field("titlename"), title.getName());
        }

        return struct;
    }

    public SourceRecord createSourceRecord(Presence data) {
        try {
            return new SourceRecord(createSourcePartition(),
                                    createSourceOffset(data),
                                    presenceTopic,
                                    PRESENCE_SCHEMA,
                                    createStruct(data));
        }
        catch (DataException de) {
            log.error("Unable to create SourceRecord {}", data);
            log.error("Data received from Xbox API doesn't match the schema defined in Connect",
                      de);
            return null;
        }
    }

    private static Map<String, Object> createSourcePartition() {
        return Collections.singletonMap(SOURCE_PARTITION, SOURCE_PARTITION);
    }

    private static Map<String, Object> createSourceOffset(Presence data) {
        return Collections.singletonMap(SOURCE_OFFSET, data.getDate().toString());
    }


    public static Instant getPersistedOffset(OffsetStorageReader persistedOffsetReader) {
        log.debug("retrieving persisted offset for previously produced Presence records");

        if (persistedOffsetReader != null) {
            Instant lastOffsetTime = getTimestampFromPersistedOffset(persistedOffsetReader);
            if (lastOffsetTime != null) {
                log.debug("offset for presences {}", lastOffsetTime);
                return lastOffsetTime;
            }
        }

        log.debug("no persisted timestamp found for presence events");
        return Instant.MIN;
    }


    private static Instant getTimestampFromPersistedOffset(OffsetStorageReader persistedOffsetReader) {
        log.debug("getting timestamp for last presence event");
        Map<String, Object> sourcePartition = createSourcePartition();
        Map<String, Object> persistedOffsetInfo = persistedOffsetReader.offset(sourcePartition);

        if (persistedOffsetInfo != null) {
            String lastOffsetTimestamp = (String) persistedOffsetInfo.get(SOURCE_OFFSET);
            return Instant.parse(lastOffsetTimestamp);
        }
        else {
            return null;
        }
    }
}
