package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class ActivityItemDeserializer implements JsonDeserializer<ActivityItem> {

	@Override
	public ActivityItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = (JsonObject) json;
		ActivityItemTypes type = ActivityItemTypes.fromString(obj.get("activityItemType").getAsString());
		switch (type) {
			case ACHIEVEMENT:
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
