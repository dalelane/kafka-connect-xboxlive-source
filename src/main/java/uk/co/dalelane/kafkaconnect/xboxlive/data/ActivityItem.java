package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ActivityItem {

    @SerializedName("activityItemType")
    @Expose
    private String activityItemType;

    @SerializedName("titleId")
    @Expose
    private String titleId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("hasUgc")
    @Expose
    private Boolean hasUgc;

    @SerializedName("contentType")
    @Expose
    private String contentType;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("itemText")
    @Expose
    private String itemText;

    @SerializedName("itemImage")
    @Expose
    private String itemImage;

    @SerializedName("shareRoot")
    @Expose
    private String shareRoot;

    @SerializedName("feedItemId")
    @Expose
    private String feedItemId;

    @SerializedName("itemRoot")
    @Expose
    private String itemRoot;

    @SerializedName("hasLiked")
    @Expose
    private Boolean hasLiked;

    @SerializedName("authorInfo")
    @Expose
    private AuthorInfo authorInfo;

    @SerializedName("userXuid")
    @Expose
    private String userXuid;

    @SerializedName("ugcCaption")
    @Expose
    private String ugcCaption;

    @SerializedName("numLikes")
    @Expose
    private Integer numLikes;

    @SerializedName("numComments")
    @Expose
    private Integer numComments;

    @SerializedName("numShares")
    @Expose
    private Integer numShares;

    @SerializedName("numViews")
    @Expose
    private Integer numViews;

    @SerializedName("timeline")
    @Expose
    private Timeline timeline;



    public String getTitleId() {
        return titleId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Boolean getHasUgc() {
        return hasUgc;
    }

    public String getActivityItemType() {
        return activityItemType;
    }
    public ActivityItemTypes getType() {
        return ActivityItemTypes.fromString(activityItemType);
    }

    public String getContentType() {
        return contentType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getItemText() {
        return itemText;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getShareRoot() {
        return shareRoot;
    }

    public String getFeedItemId() {
        return feedItemId;
    }

    public String getItemRoot() {
        return itemRoot;
    }

    public Boolean getHasLiked() {
        return hasLiked;
    }

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public String getUserXuid() {
        return userXuid;
    }

    public String getUgcCaption() {
        return ugcCaption;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public Integer getNumShares() {
        return numShares;
    }

    public Integer getNumViews() {
        return numViews;
    }


    @Override
    public int hashCode() {
        return Objects.hash(activityItemType, getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        ActivityItem other = (ActivityItem) obj;
        return Objects.equals(activityItemType, other.activityItemType) &&
                Objects.equals(getDate(), other.getDate());
    }


    @Override
    public String toString() {
        return "ActivityItem { " +
            "type=" + activityItemType + ", " +
            "xuid=" + userXuid + ", " +
            "date=" + date + ", " +
            "description=\"" + description + "\"" +
            " }";
    }
}
