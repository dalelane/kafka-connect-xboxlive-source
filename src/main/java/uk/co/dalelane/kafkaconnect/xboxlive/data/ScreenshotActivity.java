package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreenshotActivity extends MediaActivity {

    @SerializedName("screenshotId")
    @Expose
    private String screenshotId;
    
    @SerializedName("screenshotThumbnail")
    @Expose
    private String screenshotThumbnail;
    
    @SerializedName("screenshotScid")
    @Expose
    private String screenshotScid;
    
    @SerializedName("screenshotName")
    @Expose
    private String screenshotName;
    
    @SerializedName("screenshotUri")
    @Expose
    private String screenshotUri;

    
    
    public String getScreenshotId() {
        return screenshotId;
    }

    public String getScreenshotThumbnail() {
        return screenshotThumbnail;
    }

    public String getScreenshotScid() {
        return screenshotScid;
    }

    public String getScreenshotName() {
        return screenshotName;
    }

    public String getScreenshotUri() {
        return screenshotUri;
    }
}
