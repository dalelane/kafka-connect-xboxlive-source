package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GameDVRActivity extends MediaActivity {

    @SerializedName("clipId")
    @Expose
    private String clipId;

    @SerializedName("clipThumbnail")
    @Expose
    private String clipThumbnail;

    @SerializedName("downloadUri")
    @Expose
    private String downloadUri;

    @SerializedName("clipName")
    @Expose
    private String clipName;

    @SerializedName("clipCaption")
    @Expose
    private String clipCaption;

    @SerializedName("clipScid")
    @Expose
    private String clipScid;

    @SerializedName("dateRecorded")
    @Expose
    private String dateRecorded;

    @SerializedName("durationInSeconds")
    @Expose
    private Integer durationInSeconds;

    @SerializedName("creationType")
    @Expose
    private String creationType;



    public String getClipId() {
        return clipId;
    }

    public String getClipThumbnail() {
        return clipThumbnail;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public String getClipName() {
        return clipName;
    }

    public String getClipCaption() {
        return clipCaption;
    }

    public String getClipScid() {
        return clipScid;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getCreationType() {
        return creationType;
    }
}
