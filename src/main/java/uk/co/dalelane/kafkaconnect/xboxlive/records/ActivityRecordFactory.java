package uk.co.dalelane.kafkaconnect.xboxlive.records;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.storage.OffsetStorageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.AchievementActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItem;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemTypes;
import uk.co.dalelane.kafkaconnect.xboxlive.data.TextPostActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.UserPostActivity;

public class ActivityRecordFactory {

    private static Logger log = LoggerFactory.getLogger(ActivityRecordFactory.class);

    public static final String SOURCE_PARTITION = "activity";

    /** which field in an ActivityItem to use to represent the source offset */
    public static final String SOURCE_OFFSET    = "date";


    private final String achievementTopic;
    private final String containerTopic;
    private final String recommendationTopic;
    private final String dvrTopic;
    private final String screenshotTopic;
    private final String textPostTopic;
    private final String userPostTopic;
    private final String defaultTopic;

    public ActivityRecordFactory(XblConfig config) {
        achievementTopic = config.getTopicPrefix() + "ACHIEVEMENTS";
        containerTopic = config.getTopicPrefix() + "CONTAINER";
        recommendationTopic = config.getTopicPrefix() + "RECOMMENDATIONS";
        dvrTopic = config.getTopicPrefix() + "GAMEDVR";
        screenshotTopic = config.getTopicPrefix() + "SCREENSHOTS";
        textPostTopic = config.getTopicPrefix() + "TEXTPOSTS";
        userPostTopic = config.getTopicPrefix() + "USERPOSTS";
        defaultTopic = config.getTopicPrefix() + "DEFAULT";
    }

    private static final Schema ACHIEVEMENTS_SCHEMA = SchemaBuilder.struct()
        .name("achievement")
            .field("date", Schema.STRING_SCHEMA)
            .field("gamertag", Schema.STRING_SCHEMA)
            .field("gamername", Schema.STRING_SCHEMA)
            .field("name", Schema.STRING_SCHEMA)
            .field("description", Schema.STRING_SCHEMA)
            .field("icon", Schema.OPTIONAL_STRING_SCHEMA)
            .field("contentname", Schema.STRING_SCHEMA)
            .field("contentimage", Schema.STRING_SCHEMA)
            .field("platform", Schema.STRING_SCHEMA)
            .field("gamerscore", Schema.INT32_SCHEMA)
            .field("rarityscore", Schema.INT32_SCHEMA)
            .field("raritycategory", Schema.STRING_SCHEMA)
        .build();

    private static final Schema TEXT_POST_SCHEMA = SchemaBuilder.struct()
            .name("activity")
                .field("description", Schema.STRING_SCHEMA)
                .field("gamertag", Schema.OPTIONAL_STRING_SCHEMA)
                .field("gamername", Schema.OPTIONAL_STRING_SCHEMA)
                .field("date", Schema.STRING_SCHEMA)
            .build();

    private static final Schema DEFAULT_SCHEMA = SchemaBuilder.struct()
        .name("activity")
            .field("text", Schema.STRING_SCHEMA)
            .field("date", Schema.STRING_SCHEMA)
        .build();


    private static Struct createStruct(ActivityItem data) {
        Struct struct;
        switch (data.getType()) {
            case ACHIEVEMENT:
            case LEGACY_ACHIEVEMENT:
                AchievementActivity item = (AchievementActivity) data;

                struct = new Struct(ACHIEVEMENTS_SCHEMA);
                struct.put(ACHIEVEMENTS_SCHEMA.field("date"), item.getDate());
                struct.put(ACHIEVEMENTS_SCHEMA.field("gamertag"), item.getAuthorInfo().getModernGamertag());
                struct.put(ACHIEVEMENTS_SCHEMA.field("gamername"), item.getAuthorInfo().getSecondName());
                struct.put(ACHIEVEMENTS_SCHEMA.field("name"), item.getAchievementName());
                struct.put(ACHIEVEMENTS_SCHEMA.field("description"), item.getAchievementDescription());
                struct.put(ACHIEVEMENTS_SCHEMA.field("icon"), item.getAchievementIcon());
                struct.put(ACHIEVEMENTS_SCHEMA.field("contentname"), item.getContentTitle());
                struct.put(ACHIEVEMENTS_SCHEMA.field("contentimage"), item.getContentImageUri());
                struct.put(ACHIEVEMENTS_SCHEMA.field("platform"), item.getPlatform());
                struct.put(ACHIEVEMENTS_SCHEMA.field("gamerscore"), item.getGamerscore());
                struct.put(ACHIEVEMENTS_SCHEMA.field("rarityscore"), item.getRarityPercentage());
                struct.put(ACHIEVEMENTS_SCHEMA.field("raritycategory"), item.getRarityCategory());
                return struct;
            case TEXT_POST:
                TextPostActivity tpitem = (TextPostActivity) data;

                struct = new Struct(TEXT_POST_SCHEMA);
                struct.put(TEXT_POST_SCHEMA.field("description"), tpitem.getDescription());
                struct.put(TEXT_POST_SCHEMA.field("gamertag"), tpitem.getAuthorInfo().getModernGamertag());
                struct.put(TEXT_POST_SCHEMA.field("gamername"), tpitem.getAuthorInfo().getSecondName());
                struct.put(TEXT_POST_SCHEMA.field("date"), tpitem.getDate());
                return struct;
            case USER_POST:
                UserPostActivity upitem = (UserPostActivity) data;

                struct = new Struct(TEXT_POST_SCHEMA);
                struct.put(TEXT_POST_SCHEMA.field("description"), upitem.getDescription());
                struct.put(TEXT_POST_SCHEMA.field("gamertag"), upitem.getAuthorInfo().getModernGamertag());
                struct.put(TEXT_POST_SCHEMA.field("gamername"), upitem.getAuthorInfo().getSecondName());
                struct.put(TEXT_POST_SCHEMA.field("date"), upitem.getDate());
                return struct;
            case CONTAINER:
            case SOCIAL_RECOMMENDATION:
            case GAME_DVR:
            case SCREENSHOT:
            default:
                struct = new Struct(DEFAULT_SCHEMA);
                struct.put(DEFAULT_SCHEMA.field("text"), data.getItemText());
                struct.put(DEFAULT_SCHEMA.field("date"), data.getDate());
                return struct;
        }
    }

    private String getTopicName(ActivityItemTypes activityItemType) {
        switch (activityItemType) {
            case ACHIEVEMENT:
            case LEGACY_ACHIEVEMENT:
                return achievementTopic;
            case CONTAINER:
                return containerTopic;
            case SOCIAL_RECOMMENDATION:
                return recommendationTopic;
            case GAME_DVR:
                return dvrTopic;
            case SCREENSHOT:
                return screenshotTopic;
            case TEXT_POST:
                return textPostTopic;
            case USER_POST:
                return userPostTopic;
            default:
                return defaultTopic;
        }
    }

    private Schema getSchema(ActivityItemTypes activityItemType) {
        switch (activityItemType) {
            case ACHIEVEMENT:
            case LEGACY_ACHIEVEMENT:
                return ACHIEVEMENTS_SCHEMA;
            case TEXT_POST:
            case USER_POST:
                return TEXT_POST_SCHEMA;
            case CONTAINER:
            case SOCIAL_RECOMMENDATION:
            case GAME_DVR:
            case SCREENSHOT:
            default:
                return DEFAULT_SCHEMA;
        }
    }


    public SourceRecord createSourceRecord(ActivityItem data) {
        ActivityItemTypes type = data.getType();
        return new SourceRecord(createSourcePartition(type),
                                createSourceOffset(data),
                                getTopicName(type),
                                getSchema(type),
                                createStruct(data));
    }


    private static Map<String, Object> createSourcePartition(ActivityItemTypes activityType) {
        return Collections.singletonMap(SOURCE_PARTITION, activityType.toString());
    }

    private static Map<String, Object> createSourceOffset(ActivityItem data) {
        return Collections.singletonMap(SOURCE_OFFSET, data.getDate());
    }


    public static Instant getPersistedOffset(OffsetStorageReader persistedOffsetReader) {
        log.debug("retrieving persisted offset for previously produced Activity records");

        Instant offsetTimestamp = Instant.MIN;

        if (persistedOffsetReader != null) {
            ActivityItemTypes[] activityItemTypes = new ActivityItemTypes[] {
                ActivityItemTypes.ACHIEVEMENT,
                ActivityItemTypes.CONTAINER,
                ActivityItemTypes.GAME_DVR,
                ActivityItemTypes.SCREENSHOT,
                ActivityItemTypes.TEXT_POST,
                ActivityItemTypes.USER_POST,
            };

            for (ActivityItemTypes activityItemType : activityItemTypes) {
                Instant lastOffsetTime = getTimestampFromPersistedOffset(persistedOffsetReader, activityItemType);
                if (lastOffsetTime != null) {
                    log.debug("offset for " + activityItemType + " is " + lastOffsetTime.toString());

                    // is it later than achievements emitted for other activity types?
                    if (lastOffsetTime.isAfter(offsetTimestamp)) {
                        offsetTimestamp = lastOffsetTime;
                    }
                }
                else {
                    log.debug("no persisted timestamp found for {}", activityItemType.name());
                }
            }
        }

        log.debug("offset for activity items {}", offsetTimestamp);
        return offsetTimestamp;
    }


    private static Instant getTimestampFromPersistedOffset(OffsetStorageReader persistedOffsetReader, ActivityItemTypes activityItemType) {
        log.debug("getting timestamp for last event of type {}", activityItemType.name());
        Map<String, Object> sourcePartition = createSourcePartition(activityItemType);
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
