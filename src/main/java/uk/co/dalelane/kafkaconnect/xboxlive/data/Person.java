package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Person {

    @SerializedName("xuid")
    @Expose
    private String xuid;

    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite;

    @SerializedName("isFollowingCaller")
    @Expose
    private Boolean isFollowingCaller;

    @SerializedName("isFollowedByCaller")
    @Expose
    private Boolean isFollowedByCaller;

    @SerializedName("isIdentityShared")
    @Expose
    private Boolean isIdentityShared;

    @SerializedName("addedDateTimeUtc")
    @Expose
    private Object addedDateTimeUtc;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("realName")
    @Expose
    private String realName;

    @SerializedName("displayPicRaw")
    @Expose
    private String displayPicRaw;

    @SerializedName("useAvatar")
    @Expose
    private Boolean useAvatar;

    @SerializedName("gamertag")
    @Expose
    private String gamertag;

    @SerializedName("gamerScore")
    @Expose
    private String gamerScore;

    @SerializedName("xboxOneRep")
    @Expose
    private String xboxOneRep;

    @SerializedName("presenceState")
    @Expose
    private String presenceState;

    @SerializedName("presenceText")
    @Expose
    private String presenceText;

    @SerializedName("presenceDevices")
    @Expose
    private Object presenceDevices;

    @SerializedName("isBroadcasting")
    @Expose
    private Boolean isBroadcasting;

    @SerializedName("isCloaked")
    @Expose
    private Object isCloaked;

    @SerializedName("isQuarantined")
    @Expose
    private Boolean isQuarantined;

    @SerializedName("suggestion")
    @Expose
    private Object suggestion;

    @SerializedName("recommendation")
    @Expose
    private Recommendation recommendation;

    @SerializedName("titleHistory")
    @Expose
    private Object titleHistory;

    @SerializedName("multiplayerSummary")
    @Expose
    private Object multiplayerSummary;

    @SerializedName("recentPlayer")
    @Expose
    private Object recentPlayer;

    @SerializedName("follower")
    @Expose
    private Object follower;

    @SerializedName("preferredColor")
    @Expose
    private Color preferredColor;

    @SerializedName("presenceDetails")
    @Expose
    private Object presenceDetails;

    @SerializedName("titlePresence")
    @Expose
    private Object titlePresence;

    @SerializedName("titleSummaries")
    @Expose
    private Object titleSummaries;

    @SerializedName("presenceTitleIds")
    @Expose
    private Object presenceTitleIds;

    @SerializedName("detail")
    @Expose
    private Object detail;

    @SerializedName("communityManagerTitles")
    @Expose
    private Object communityManagerTitles;

    @SerializedName("socialManager")
    @Expose
    private Object socialManager;

    @SerializedName("broadcast")
    @Expose
    private Object broadcast;

    @SerializedName("tournamentSummary")
    @Expose
    private Object tournamentSummary;

    @SerializedName("avatar")
    @Expose
    private Object avatar;



    public String getXuid() {
        return xuid;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public Boolean getIsFollowingCaller() {
        return isFollowingCaller;
    }

    public Boolean getIsFollowedByCaller() {
        return isFollowedByCaller;
    }

    public Boolean getIsIdentityShared() {
        return isIdentityShared;
    }

    public Object getAddedDateTimeUtc() {
        return addedDateTimeUtc;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRealName() {
        return realName;
    }

    public String getDisplayPicRaw() {
        return displayPicRaw;
    }

    public Boolean getUseAvatar() {
        return useAvatar;
    }

    public String getGamertag() {
        return gamertag;
    }

    public String getGamerScore() {
        return gamerScore;
    }

    public String getXboxOneRep() {
        return xboxOneRep;
    }

    public String getPresenceState() {
        return presenceState;
    }

    public String getPresenceText() {
        return presenceText;
    }

    public Object getPresenceDevices() {
        return presenceDevices;
    }

    public Boolean getIsBroadcasting() {
        return isBroadcasting;
    }

    public Object getIsCloaked() {
        return isCloaked;
    }

    public Boolean getIsQuarantined() {
        return isQuarantined;
    }

    public Object getSuggestion() {
        return suggestion;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public Object getTitleHistory() {
        return titleHistory;
    }

    public Object getMultiplayerSummary() {
        return multiplayerSummary;
    }

    public Object getRecentPlayer() {
        return recentPlayer;
    }

    public Object getFollower() {
        return follower;
    }

    public Color getPreferredColor() {
        return preferredColor;
    }

    public Object getPresenceDetails() {
        return presenceDetails;
    }

    public Object getTitlePresence() {
        return titlePresence;
    }

    public Object getTitleSummaries() {
        return titleSummaries;
    }

    public Object getPresenceTitleIds() {
        return presenceTitleIds;
    }

    public Object getDetail() {
        return detail;
    }

    public Object getCommunityManagerTitles() {
        return communityManagerTitles;
    }

    public Object getSocialManager() {
        return socialManager;
    }

    public Object getBroadcast() {
        return broadcast;
    }

    public Object getTournamentSummary() {
        return tournamentSummary;
    }

    public Object getAvatar() {
        return avatar;
    }
}
