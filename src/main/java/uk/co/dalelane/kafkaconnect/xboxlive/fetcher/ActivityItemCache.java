package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityFeed;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItem;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ActivityItemComparator;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ContainerActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.GameDVRActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.ScreenshotActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.SocialRecommendationActivity;


public class ActivityItemCache {

    private static Logger log = LoggerFactory.getLogger(ActivityItemCache.class);

    /**
     * Queue of events waiting in the cache to be retrieved by
     *  Kafka Connect.
     */
    private final SortedSet<ActivityItem> activityItemsCache;

    /**
     * Timestamp of the most recent activity item to be added to the
     *  cache. Used to minimize the number of duplicate events emitted.
     */
    private Instant offsetTimestamp;



    public ActivityItemCache(Instant startTimestamp) {
        log.debug("Creating activity item cache for activity items newer than {}", startTimestamp);
        activityItemsCache = new TreeSet<>(new ActivityItemComparator());
        offsetTimestamp = startTimestamp;
    }


    public synchronized void addFeed(ActivityFeed activityFeed) {
        log.info("Adding activity items to cache");

        for (ActivityItem ai : activityFeed.getActivityItems()) {
            if (ai instanceof ContainerActivity) {
                ContainerActivity cai = (ContainerActivity) ai;
                for (ActivityItem fi : cai.getFeedItems()) {
                    addItemToFeed(fi);
                }
            }
            else {
                addItemToFeed(ai);
            }
        }
    }



    private void addItemToFeed(ActivityItem item) {
        if (shouldIgnore(item)) {
            log.debug("ignoring item based on type {}", item);
        }
        else if (getTimestamp(item).isAfter(offsetTimestamp)) {
            activityItemsCache.add(item);
            offsetTimestamp = getTimestamp(item);

            log.info("adding {}", item);
        }
        else {
            log.debug("ignoring old item {}", item);
        }
    }


    // ignoring some items in the activity feed for now... to be
    //  added in a future version once I decide what to do with them
    private boolean shouldIgnore(ActivityItem item) {
        return item instanceof SocialRecommendationActivity ||
            item instanceof GameDVRActivity ||
            item instanceof ScreenshotActivity;
    }


    public synchronized List<ActivityItem> getActivityItems() {
        List<ActivityItem> items = new ArrayList<>();

        while (activityItemsCache.isEmpty() == false) {
            ActivityItem nextItem = activityItemsCache.first();

            boolean removed = activityItemsCache.remove(nextItem);
            if (!removed) {
                log.error("failed to remove item from cache {}", nextItem);
            }
            else {
                items.add(nextItem);
            }
        }

        return items;
    }

    private Instant getTimestamp(ActivityItem item) {
        return Instant.parse(item.getDate());
    }
}
