package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;


public class PresenceCache {

    private static Logger log = LoggerFactory.getLogger(PresenceCache.class);

    private final Queue<Presence> presenceItemsQueue;
    private final Map<String, Presence> latestPresence;

    public PresenceCache() {
    	presenceItemsQueue = new ConcurrentLinkedQueue<>();
    	latestPresence = new HashMap<>();
    }


	public void init(Collection<Presence> initialPresences) {
		log.info("Getting initial set of presences to recognize change events");
		for (Presence presence : initialPresences) {
			latestPresence.put(presence.getXuid(), presence);
		}
	}


    public synchronized void addPresences(Collection<Presence> presences) {
    	log.info("Adding presences to cache");

        int numberAdded = 0;
        for (Presence presence : presences) {
        	String userid = presence.getXuid();
        	if (latestPresence.containsKey(userid) &&
        		latestPresence.get(userid).equals(presence))
        	{
        		// unchanged - ignoring
        	}
        	else {
        		latestPresence.put(userid, presence);
        		presenceItemsQueue.add(presence);
        		numberAdded += 1;

        		log.info("adding {}", presence);
        	}
        }

        log.debug("added items {}", numberAdded);
    }


    public synchronized List<Presence> getPresences() {
        List<Presence> items = new ArrayList<>();

        Presence nextItem = presenceItemsQueue.poll();

        while (nextItem != null) {
        	items.add(nextItem);

        	nextItem = presenceItemsQueue.poll();
        }

        return items;
    }
}
