package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UnfollowableTitle {

    @SerializedName("titleId")
    @Expose
    private Integer titleId;

    @SerializedName("titleName")
    @Expose
    private String titleName;



    public Integer getTitleId() {
        return titleId;
    }

    public String getTitleName() {
        return titleName;
    }
}
