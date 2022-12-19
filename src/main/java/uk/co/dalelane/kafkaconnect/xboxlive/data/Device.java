package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Device {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("titles")
    @Expose
    private List<Title> titles = null;



    public String getType() {
        return type;
    }

    public List<Title> getTitles() {
        return titles;
    }
}
