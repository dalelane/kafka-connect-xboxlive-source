package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LinkData {

    @SerializedName("youtubeId")
    @Expose
    private String youtubeId;

    @SerializedName("embedUrl")
    @Expose
    private String embedUrl;



    public String getYoutubeId() {
        return youtubeId;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }
}
