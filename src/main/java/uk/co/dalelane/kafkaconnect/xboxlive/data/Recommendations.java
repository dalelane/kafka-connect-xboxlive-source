package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Recommendations {

    @SerializedName("people")
    @Expose
    private List<Person> people = null;

    @SerializedName("recommendationSummary")
    @Expose
    private RecommendationSummary recommendationSummary;

    @SerializedName("friendFinderState")
    @Expose
    private FriendFinderState friendFinderState;



    public List<Person> getPeople() {
        return people;
    }

    public RecommendationSummary getRecommendationSummary() {
        return recommendationSummary;
    }

    public FriendFinderState getFriendFinderState() {
        return friendFinderState;
    }
}
