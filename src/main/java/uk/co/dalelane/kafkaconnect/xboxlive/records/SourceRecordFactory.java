package uk.co.dalelane.kafkaconnect.xboxlive.records;

import java.util.Collections;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import uk.co.dalelane.kafkaconnect.xboxlive.XblConfig;
import uk.co.dalelane.kafkaconnect.xboxlive.data.AchievementActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItem;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemTypes;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;
import uk.co.dalelane.kafkaconnect.xboxlive.data.Title;

public class SourceRecordFactory {

    public static final String SOURCE_OFFSET = "date";
    public static final String SOURCE_PARTITION = "activity";

    private final String presenceTopic;
    private final String achievementTopic;
    private final String containerTopic;
    private final String recommendationTopic;
    private final String dvrTopic;
    private final String screenshotTopic;
    private final String textPostTopic;
    private final String userPostTopic;
    private final String defaultTopic;

    public SourceRecordFactory(XblConfig config) {
        presenceTopic = config.getTopicPrefix() + "PRESENCE";
        achievementTopic = config.getTopicPrefix() + "ACHIEVEMENTS";
        containerTopic = config.getTopicPrefix() + "CONTAINER";
        recommendationTopic = config.getTopicPrefix() + "RECOMMENDATIONS";
        dvrTopic = config.getTopicPrefix() + "GAMEDVR";
        screenshotTopic = config.getTopicPrefix() + "SCREENSHOTS";
        textPostTopic = config.getTopicPrefix() + "TEXTPOSTS";
        userPostTopic = config.getTopicPrefix() + "USERPOSTS";
        defaultTopic = config.getTopicPrefix() + "DEFAULT";
    }

    private static final Schema PRESENCE_SCHEMA = SchemaBuilder.struct().name("presence")
            .field("date", Schema.STRING_SCHEMA)
            .field("userid", Schema.STRING_SCHEMA)
            .field("state", Schema.STRING_SCHEMA)
            .field("titleid", Schema.OPTIONAL_STRING_SCHEMA)
            .field("titlename", Schema.OPTIONAL_STRING_SCHEMA)
            .build();

    private static final Schema ACHIEVEMENTS_SCHEMA = SchemaBuilder.struct().name("achievement")
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

    private static final Schema DEFAULT_SCHEMA = SchemaBuilder.struct().name("activity")
            .field("text", Schema.STRING_SCHEMA)
            .field("date", Schema.STRING_SCHEMA)
            .build();


    private static Struct createStruct(ActivityItem data) {
        Struct struct;
        switch (data.getType()) {
            case ACHIEVEMENT:
            case LEGACY_ACHIEVEMENT:
                AchievementActivity item = (AchievementActivity) data;

                struct =  new Struct(ACHIEVEMENTS_SCHEMA);
                struct.put(ACHIEVEMENTS_SCHEMA.field("date"), data.getDate());
                struct.put(ACHIEVEMENTS_SCHEMA.field("gamertag"), data.getAuthorInfo().getModernGamertag());
                struct.put(ACHIEVEMENTS_SCHEMA.field("gamername"), data.getAuthorInfo().getSecondName());
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
            case CONTAINER:
            case SOCIAL_RECOMMENDATION:
            case GAME_DVR:
            case SCREENSHOT:
            case TEXT_POST:
            case USER_POST:
            default:
                struct = new Struct(DEFAULT_SCHEMA);
                struct.put(DEFAULT_SCHEMA.field("text"), data.getItemText());
                struct.put(DEFAULT_SCHEMA.field("date"), data.getDate());
                return struct;
        }
    }

    private static Struct createStruct(Presence data) {
        Struct struct = new Struct(PRESENCE_SCHEMA);
        struct.put(PRESENCE_SCHEMA.field("date"), data.getDate());
        struct.put(PRESENCE_SCHEMA.field("userid"), data.getXuid());
        struct.put(PRESENCE_SCHEMA.field("state"), data.getState());
        Title title = data.getFullTitle();
        if (title != null) {
            struct.put(PRESENCE_SCHEMA.field("titleid"), title.getId());
            struct.put(PRESENCE_SCHEMA.field("titlename"), title.getName());
        }
        return struct;
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
            case CONTAINER:
            case SOCIAL_RECOMMENDATION:
            case GAME_DVR:
            case SCREENSHOT:
            case TEXT_POST:
            case USER_POST:
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
    public SourceRecord createSourceRecord(Presence data) {
        return new SourceRecord(createSourcePartition(data),
                                createSourceOffset(data),
                                presenceTopic,
                                PRESENCE_SCHEMA,
                                createStruct(data));
    }



    private static Map<String, Object> createSourceOffset(Presence data) {
        return Collections.singletonMap(SOURCE_OFFSET, data.getDate());
    }
    public static Map<String, Object> createSourcePartition(Presence data) {
        return Collections.singletonMap(SOURCE_PARTITION, data.getXuid());
    }


    private static Map<String, Object> createSourceOffset(ActivityItem data) {
        return Collections.singletonMap(SOURCE_OFFSET, data.getDate());
    }

    public static Map<String, Object> createSourcePartition(ActivityItemTypes activityType) {
        return Collections.singletonMap(SOURCE_PARTITION, activityType.toString());
    }
}
