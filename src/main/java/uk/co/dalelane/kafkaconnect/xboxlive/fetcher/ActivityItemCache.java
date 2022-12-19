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
import uk.co.dalelane.kafkaconnect.xboxlive.data.TextPostActivity;
import uk.co.dalelane.kafkaconnect.xboxlive.data.UserPostActivity;


public class ActivityItemCache {

    private static Logger log = LoggerFactory.getLogger(ActivityItemCache.class);

    private final SortedSet<ActivityItem> activityItemsCache;

    private Instant offsetTimestamp;

    public ActivityItemCache(Instant startTimestamp) {
    	activityItemsCache = new TreeSet<>(new ActivityItemComparator());
    	offsetTimestamp = startTimestamp;
    }


    public synchronized void addFeed(ActivityFeed activityFeed) {
    	log.info("Adding activity items to cache");

        int numberAdded = 0;
        for (ActivityItem ai : activityFeed.getActivityItems()) {
        	if (ai instanceof ContainerActivity) {
        		ContainerActivity cai = (ContainerActivity) ai;
        		for (ActivityItem fi : cai.getFeedItems()) {
        			numberAdded += addItemToFeed(fi);
        		}
        	}
        	else {
        		numberAdded += addItemToFeed(ai);
        	}
        }

        log.debug("added items {}", numberAdded);
    }



    private int addItemToFeed(ActivityItem item) {
    	if (shouldIgnore(item)) {
    		log.debug("ignoring {}", item);
    	}
    	else if (getTimestamp(item).compareTo(offsetTimestamp) > 0) {
    		activityItemsCache.add(item);
    		log.info("adding {}", item);
    		return 1;
    	}
    	// else already added to feed
    	return 0;
    }


    // ignoring some items in the activity feed for now... to be
    //  added in a future version once I decide what to do with them
    private boolean shouldIgnore(ActivityItem item) {
    	return item instanceof SocialRecommendationActivity ||
			item instanceof GameDVRActivity ||
			item instanceof ScreenshotActivity ||
			item instanceof TextPostActivity ||
			item instanceof UserPostActivity;
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
            	offsetTimestamp = getTimestamp(nextItem);

            	items.add(nextItem);
            }
        }

        return items;
    }

    private Instant getTimestamp(ActivityItem item) {
    	return Instant.parse(item.getDate());
    }
}
