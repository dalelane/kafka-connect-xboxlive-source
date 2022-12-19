package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Color {

    @SerializedName("primaryColor")
    @Expose
    private String primaryColor;

    @SerializedName("secondaryColor")
    @Expose
    private String secondaryColor;

    @SerializedName("tertiaryColor")
    @Expose
    private String tertiaryColor;



    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public String getTertiaryColor() {
        return tertiaryColor;
    }
}
