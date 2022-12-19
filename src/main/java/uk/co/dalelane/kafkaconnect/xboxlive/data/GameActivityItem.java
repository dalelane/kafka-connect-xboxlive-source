package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public abstract class GameActivityItem extends ActivityItem {

    @SerializedName("contentImageUri")
    @Expose
    private String contentImageUri;

    @SerializedName("contentTitle")
    @Expose
    private String contentTitle;

    @SerializedName("platform")
    @Expose
    private String platform;



    public String getContentImageUri() {
        return contentImageUri;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getPlatform() {
        return platform;
    }
}
