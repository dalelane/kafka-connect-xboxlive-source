package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Recommendation {

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Reasons")
    @Expose
    private List<String> reasons = null;



    public String getType() {
        return type;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
