package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContainerActivity extends ActivityItem {

    @SerializedName("itemSource")
    @Expose
    private String itemSource;

    @SerializedName("feedItems")
    @Expose
    private List<ActivityItem> feedItems = null;

    @SerializedName("postType")
    @Expose
    private String postType;

    @SerializedName("postDetails")
    @Expose
    private PostDetails postDetails;



    public String getItemSource() {
        return itemSource;
    }

    public List<ActivityItem> getFeedItems() {
        return feedItems;
    }

    public String getPostType() {
        return postType;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }
}
