package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AchievementActivity extends GameActivityItem {

    @SerializedName("achievementName")
    @Expose
    private String achievementName;

    @SerializedName("achievementDescription")
    @Expose
    private String achievementDescription;

    @SerializedName("isSecret")
    @Expose
    private Boolean isSecret;

    @SerializedName("hasAppAward")
    @Expose
    private Boolean hasAppAward;

    @SerializedName("hasArtAward")
    @Expose
    private Boolean hasArtAward;

    @SerializedName("achievementScid")
    @Expose
    private String achievementScid;

    @SerializedName("achievementId")
    @Expose
    private String achievementId;

    @SerializedName("achievementType")
    @Expose
    private String achievementType;

    @SerializedName("achievementIcon")
    @Expose
    private String achievementIcon;

    @SerializedName("rarityCategory")
    @Expose
    private String rarityCategory;

    @SerializedName("rarityPercentage")
    @Expose
    private Integer rarityPercentage;

    @SerializedName("gamerscore")
    @Expose
    private Integer gamerscore;



    public String getAchievementScid() {
        return achievementScid;
    }

    public String getAchievementId() {
        return achievementId;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public String getAchievementIcon() {
        return achievementIcon;
    }

    public String getRarityCategory() {
        return rarityCategory;
    }

    public Integer getRarityPercentage() {
        return rarityPercentage;
    }

    public Integer getGamerscore() {
        return gamerscore;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public Boolean getIsSecret() {
        return isSecret;
    }

    public Boolean getHasAppAward() {
        return hasAppAward;
    }

    public Boolean getHasArtAward() {
        return hasArtAward;
    }


    @Override
    public String toString() {
        return "AchievementActivity { " +
            "user=" + getUserXuid() + ", " +
            "date=" + getDate() + ", " +
            "name=" + achievementName +
            " }";
    }
}