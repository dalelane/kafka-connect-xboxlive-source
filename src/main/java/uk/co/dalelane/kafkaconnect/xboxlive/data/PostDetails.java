package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostDetails {

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("displayLink")
    @Expose
    private String displayLink;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("linkType")
    @Expose
    private String linkType;

    @SerializedName("linkData")
    @Expose
    private LinkData linkData;



    public String getLink() {
        return link;
    }

    public String getDisplayLink() {
        return displayLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLinkType() {
        return linkType;
    }

    public LinkData getLinkData() {
        return linkData;
    }
}
