package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LastSeen {

    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    @SerializedName("titleId")
    @Expose
    private String titleId;

    @SerializedName("titleName")
    @Expose
    private String titleName;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;



    public String getDeviceType() {
        return deviceType;
    }

    public String getTitleId() {
        return titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
