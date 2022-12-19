package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Timeline {

    @SerializedName("timelineId")
    @Expose
    private String timelineId;

    @SerializedName("timelineType")
    @Expose
    private String timelineType;

    @SerializedName("timelineName")
    @Expose
    private String timelineName;

    @SerializedName("timelineImage")
    @Expose
    private String timelineImage;

    @SerializedName("isOfficialClub")
    @Expose
    private Boolean isOfficialClub;

    @SerializedName("unfollowableTitles")
    @Expose
    private List<UnfollowableTitle> unfollowableTitles = null;

    @SerializedName("authorRoles")
    @Expose
    private List<String> authorRoles = null;

    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;



    public String getTimelineId() {
        return timelineId;
    }

    public String getTimelineType() {
        return timelineType;
    }

    public String getTimelineName() {
        return timelineName;
    }

    public String getTimelineImage() {
        return timelineImage;
    }

    public Boolean getIsOfficialClub() {
        return isOfficialClub;
    }

    public List<UnfollowableTitle> getUnfollowableTitles() {
        return unfollowableTitles;
    }

    public List<String> getAuthorRoles() {
        return authorRoles;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }
}
