package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FriendFinderState {

    @SerializedName("facebookOptInStatus")
    @Expose
    private String facebookOptInStatus;

    @SerializedName("facebookTokenStatus")
    @Expose
    private String facebookTokenStatus;

    @SerializedName("phoneOptInStatus")
    @Expose
    private String phoneOptInStatus;

    @SerializedName("phoneTokenStatus")
    @Expose
    private String phoneTokenStatus;

    @SerializedName("steamTokenStatus")
    @Expose
    private String steamTokenStatus;

    @SerializedName("steamOptInStatus")
    @Expose
    private String steamOptInStatus;

    @SerializedName("discordTokenStatus")
    @Expose
    private String discordTokenStatus;

    @SerializedName("discordOptInStatus")
    @Expose
    private String discordOptInStatus;

    @SerializedName("instagramTokenStatus")
    @Expose
    private String instagramTokenStatus;

    @SerializedName("instagramOptInStatus")
    @Expose
    private String instagramOptInStatus;

    @SerializedName("mixerTokenStatus")
    @Expose
    private String mixerTokenStatus;

    @SerializedName("mixerOptInStatus")
    @Expose
    private String mixerOptInStatus;

    @SerializedName("redditTokenStatus")
    @Expose
    private String redditTokenStatus;

    @SerializedName("redditOptInStatus")
    @Expose
    private String redditOptInStatus;

    @SerializedName("twitchTokenStatus")
    @Expose
    private String twitchTokenStatus;

    @SerializedName("twitchOptInStatus")
    @Expose
    private String twitchOptInStatus;

    @SerializedName("twitterTokenStatus")
    @Expose
    private String twitterTokenStatus;

    @SerializedName("twitterOptInStatus")
    @Expose
    private String twitterOptInStatus;

    @SerializedName("youTubeTokenStatus")
    @Expose
    private String youTubeTokenStatus;

    @SerializedName("youTubeOptInStatus")
    @Expose
    private String youTubeOptInStatus;



    public String getFacebookOptInStatus() {
        return facebookOptInStatus;
    }

    public String getFacebookTokenStatus() {
        return facebookTokenStatus;
    }

    public String getPhoneOptInStatus() {
        return phoneOptInStatus;
    }

    public String getPhoneTokenStatus() {
        return phoneTokenStatus;
    }

    public String getSteamTokenStatus() {
        return steamTokenStatus;
    }

    public String getSteamOptInStatus() {
        return steamOptInStatus;
    }

    public String getDiscordTokenStatus() {
        return discordTokenStatus;
    }

    public String getDiscordOptInStatus() {
        return discordOptInStatus;
    }

    public String getInstagramTokenStatus() {
        return instagramTokenStatus;
    }

    public String getInstagramOptInStatus() {
        return instagramOptInStatus;
    }

    public String getMixerTokenStatus() {
        return mixerTokenStatus;
    }

    public String getMixerOptInStatus() {
        return mixerOptInStatus;
    }

    public String getRedditTokenStatus() {
        return redditTokenStatus;
    }

    public String getRedditOptInStatus() {
        return redditOptInStatus;
    }

    public String getTwitchTokenStatus() {
        return twitchTokenStatus;
    }

    public String getTwitchOptInStatus() {
        return twitchOptInStatus;
    }

    public String getTwitterTokenStatus() {
        return twitterTokenStatus;
    }

    public String getTwitterOptInStatus() {
        return twitterOptInStatus;
    }

    public String getYouTubeTokenStatus() {
        return youTubeTokenStatus;
    }

    public String getYouTubeOptInStatus() {
        return youTubeOptInStatus;
    }
}
