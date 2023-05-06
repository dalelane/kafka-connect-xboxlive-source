package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Presence {

    @SerializedName("xuid")
    @Expose
    private String xuid;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;

    @SerializedName("lastSeen")
    @Expose
    private LastSeen lastSeen;


    private Instant objectCreationDate = Instant.now();


    public String getXuid() {
        return xuid;
    }

    public String getState() {
        return state;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public LastSeen getLastSeen() {
        return lastSeen;
    }

    public Instant getDate() {
        // prefer the timestamp for the mentioned title if available
        Title title = getFullTitle();
        if (title != null && title.getLastModified() != null) {
            return title.getLastModified();
        }

        // default to object creation timestamp otherwise
        return objectCreationDate;
    }

    public Title getFullTitle() {
        Title fullTitle = null;
        if (devices != null && devices.isEmpty() == false) {
            Device firstDevice = devices.get(0);
            if (firstDevice.getTitles() != null) {
                Optional<Title> title = firstDevice.getTitles()
                    .stream()
                    .filter(t -> t.getPlacement().equals("Full"))
                    .sorted(Comparator.comparing(Title::getLastModified).reversed())
                    .findFirst();
                if (title.isPresent()) {
                    fullTitle = title.get();
                }
            }
        }
        return fullTitle;
    }

    @Override
    public int hashCode() {
        // hash based on user id
        return Objects.hash(xuid);
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

        Presence other = (Presence) obj;
        if (xuid.equals(other.xuid) == false) {
            return false;
        }

        // if we're here, we've got two
        //  presence objects for the same user

        if ("Online".equals(state) && "Online".equals(other.state)) {
            Title title = getFullTitle();
            Title otherTitle = other.getFullTitle();

            if (title == null && otherTitle == null) {
                return true;
            }
            if (title == null || otherTitle == null) {
                return false;
            }
            return title.equals(otherTitle);
        }

        return state.equals(other.state);
    }


    @Override
    public String toString() {
        return "Presence { " +
            "xuid=" + xuid + ", " +
            "state=" + state +
            " }";
    }
}
