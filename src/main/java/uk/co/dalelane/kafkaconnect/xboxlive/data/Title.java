package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.time.Instant;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Title {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("placement")
    @Expose
    private String placement;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("lastModified")
    @Expose
    private String lastModified;



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlacement() {
        return placement;
    }

    public String getState() {
        return state;
    }

    public String getLastModified() {
        return lastModified;
    }

    public Instant getLastModifiedInstant() {
    	return Instant.parse(lastModified);
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Title other = (Title) obj;
		return Objects.equals(id, other.id) &&
				Objects.equals(state, other.state);
	}
}
