package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RecommendationSummary {

    @SerializedName("friendOfFriend")
    @Expose
    private Integer friendOfFriend;

    @SerializedName("facebookFriend")
    @Expose
    private Integer facebookFriend;

    @SerializedName("phoneContact")
    @Expose
    private Integer phoneContact;

    @SerializedName("follower")
    @Expose
    private Integer follower;

    @SerializedName("VIP")
    @Expose
    private Integer vip;

    @SerializedName("promoteSuggestions")
    @Expose
    private Boolean promoteSuggestions;



    public Integer getFriendOfFriend() {
        return friendOfFriend;
    }

    public Integer getFacebookFriend() {
        return facebookFriend;
    }

    public Integer getPhoneContact() {
        return phoneContact;
    }

    public Integer getFollower() {
        return follower;
    }

    public Integer getVip() {
        return vip;
    }

    public Boolean getPromoteSuggestions() {
        return promoteSuggestions;
    }
}
