package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.util.Comparator;

public class PresenceTimestampComparator implements Comparator<Presence> {

    @Override
    public int compare(Presence o1, Presence o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
