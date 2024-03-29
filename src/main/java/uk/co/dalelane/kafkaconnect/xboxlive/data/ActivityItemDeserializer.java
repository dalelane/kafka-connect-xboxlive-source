package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class ActivityItemDeserializer implements JsonDeserializer<ActivityItem> {

    private static Logger log = LoggerFactory.getLogger(ActivityItemDeserializer.class);


    @Override
    public ActivityItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = (JsonObject) json;

        ActivityItemTypes type = ActivityItemTypes.fromString(obj.get("activityItemType").getAsString());
        if (type == null) {
            log.error("activity type missing {}", obj);
            return context.deserialize(json, ActivityItem.class);
        }

        switch (type) {
            case ACHIEVEMENT:
            case LEGACY_ACHIEVEMENT:
                return context.deserialize(json, AchievementActivity.class);
            case CONTAINER:
                return context.deserialize(json, ContainerActivity.class);
            case SOCIAL_RECOMMENDATION:
                return context.deserialize(json, SocialRecommendationActivity.class);
            case GAME_DVR:
                return context.deserialize(json, GameDVRActivity.class);
            case SCREENSHOT:
                return context.deserialize(json, ScreenshotActivity.class);
            case TEXT_POST:
                return context.deserialize(json, TextPostActivity.class);
            case USER_POST:
                return context.deserialize(json, UserPostActivity.class);
            default:
                return context.deserialize(json, ActivityItem.class);
        }
    }
}
