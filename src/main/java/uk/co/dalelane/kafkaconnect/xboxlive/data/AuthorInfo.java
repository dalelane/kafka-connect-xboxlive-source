package uk.co.dalelane.kafkaconnect.xboxlive.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthorInfo {

    @SerializedName("modernGamertag")
    @Expose
    private String modernGamertag;

    @SerializedName("modernGamertagSuffix")
    @Expose
    private String modernGamertagSuffix;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("secondName")
    @Expose
    private String secondName;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("color")
    @Expose
    private Color color;

    @SerializedName("showAsAvatar")
    @Expose
    private String showAsAvatar;

    @SerializedName("authorType")
    @Expose
    private String authorType;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("pageId")
    @Expose
    private String pageId;

    @SerializedName("pageName")
    @Expose
    private String pageName;

    @SerializedName("pageImage")
    @Expose
    private String pageImage;



    public String getModernGamertag() {
        return modernGamertag;
    }

    public String getModernGamertagSuffix() {
        return modernGamertagSuffix;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Color getColor() {
        return color;
    }

    public String getShowAsAvatar() {
        return showAsAvatar;
    }

    public String getAuthorType() {
        return authorType;
    }

    public String getId() {
        return id;
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public String getPageImage() {
        return pageImage;
    }
}
