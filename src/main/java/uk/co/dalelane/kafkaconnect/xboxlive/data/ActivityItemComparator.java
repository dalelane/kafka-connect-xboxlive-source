package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.time.Instant;
import java.util.Comparator;


public class ActivityItemComparator implements Comparator<ActivityItem> {

    @Override
    public int compare(ActivityItem o1, ActivityItem o2) {
        Instant i1 = Instant.parse(o1.getDate());
        Instant i2 = Instant.parse(o2.getDate());
        return i1.compareTo(i2);
    }
}
