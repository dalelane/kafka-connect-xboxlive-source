package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GameMediaContentLocator {

    @SerializedName("Expiration")
    @Expose
    private String expiration;

    @SerializedName("FileSize")
    @Expose
    private Integer fileSize;

    @SerializedName("LocatorType")
    @Expose
    private String locatorType;

    @SerializedName("Uri")
    @Expose
    private String uri;



    public String getExpiration() {
        return expiration;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public String getUri() {
        return uri;
    }
}
