package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaActivity extends GameActivityItem {

    @SerializedName("gameMediaContentLocators")
    @Expose
    private List<GameMediaContentLocator> gameMediaContentLocators = null;

    @SerializedName("viewCount")
    @Expose
    private Integer viewCount;

    @SerializedName("uploadTitleId")
    @Expose
    private String uploadTitleId;

    

    public List<GameMediaContentLocator> getGameMediaContentLocators() {
        return gameMediaContentLocators;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public String getUploadTitleId() {
        return uploadTitleId;
    }
}
