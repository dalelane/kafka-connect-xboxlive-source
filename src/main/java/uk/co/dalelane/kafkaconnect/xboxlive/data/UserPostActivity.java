package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPostActivity extends ActivityItem {

    @SerializedName("postType")
    @Expose
    private String postType;

    @SerializedName("postDetails")
    @Expose
    private PostDetails postDetails;

    @SerializedName("trustedItemImage")
    @Expose
    private Boolean trustedItemImage;



    public String getPostType() {
        return postType;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public Boolean getTrustedItemImage() {
        return trustedItemImage;
    }
}
