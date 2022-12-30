package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.time.Instant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialRecommendationActivity extends ActivityItem {

    private String nonNullDate;

    @SerializedName("recommendations")
    @Expose
    private Recommendations recommendations;


    public Recommendations getRecommendations() {
        return recommendations;
    }

    @Override
    public String getDate() {
        if (nonNullDate == null) {
            nonNullDate = super.getDate();
            if (nonNullDate == null) {
                nonNullDate = Instant.now().toString();
            }
        }
        return nonNullDate;
    }
}
