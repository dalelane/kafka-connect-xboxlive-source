package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum ActivityItemTypes {
	ACHIEVEMENT("Achievement"),
	LEGACY_ACHIEVEMENT("LegacyAchievement"),
	CONTAINER("Container"),
	SOCIAL_RECOMMENDATION("SocialRecommendation"),
	GAME_DVR("GameDVR"),
	SCREENSHOT("Screenshot"),
	TEXT_POST("TextPost"),
	USER_POST("UserPost");

	private final String text;

	ActivityItemTypes(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}


	private static final Map <String, ActivityItemTypes> ENUM_MAP;

	static {
        Map<String, ActivityItemTypes> map = new ConcurrentHashMap<String, ActivityItemTypes>();
        for (ActivityItemTypes instance : ActivityItemTypes.values()) {
            map.put(instance.text, instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

	public static final ActivityItemTypes fromString (String label) {
        return ENUM_MAP.get(label);
    }
}
