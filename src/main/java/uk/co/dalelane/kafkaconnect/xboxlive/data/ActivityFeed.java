package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ActivityFeed {

    @SerializedName("numItems")
    @Expose
    private Integer numItems;

    @SerializedName("activityItems")
    @Expose
    private List<ActivityItem> activityItems = null;

    @SerializedName("pollingToken")
    @Expose
    private String pollingToken;

    @SerializedName("pollingIntervalSeconds")
    @Expose
    private String pollingIntervalSeconds;

    @SerializedName("contToken")
    @Expose
    private String contToken;



    public Integer getNumItems() {
        return numItems;
    }

    public List<ActivityItem> getActivityItems() {
        return activityItems;
    }

    public String getPollingToken() {
        return pollingToken;
    }

    public String getPollingIntervalSeconds() {
        return pollingIntervalSeconds;
    }

    public String getContToken() {
        return contToken;
    }
}
