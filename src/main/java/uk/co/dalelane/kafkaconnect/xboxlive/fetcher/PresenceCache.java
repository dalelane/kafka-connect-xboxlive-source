package uk.co.dalelane.kafkaconnect.xboxlive.fetcher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.dalelane.kafkaconnect.xboxlive.data.Presence;
import uk.co.dalelane.kafkaconnect.xboxlive.data.PresenceTimestampComparator;


public class PresenceCache {

    private static Logger log = LoggerFactory.getLogger(PresenceCache.class);

    /**
     * Queue of events waiting in the cache to be retrieved by
     *  Kafka Connect.
     */
    private final SortedSet<Presence> presenceItemsCache;

    /**
     * The most recent presence event for each gamer, indexed by gamer userid.
     */
    private final Map<String, Presence> latestPresence;

    /**
     * Committed offset timestamp from the previous run of the Connector.
     *  Used to minimize the number of duplicate events emitted when the
     *  Connector restarts.
     *
     *  (This should ensure that only duplicate "offline" events are
     *   emitted, which can be treated as idempotent).
     */
    private Instant initialTimestamp;



    public PresenceCache(Instant startTimestamp) {
        log.debug("Creating presence cache for presence events newer than {}", startTimestamp);
        presenceItemsCache = new TreeSet<>(new PresenceTimestampComparator());
        latestPresence = new HashMap<>();
        initialTimestamp = startTimestamp;
    }


    public synchronized void addPresences(Collection<Presence> presences) {
        log.info("Adding presences to cache");

        for (Presence presence : presences) {
            String userid = presence.getXuid();
            if (latestPresence.containsKey(userid) &&
                latestPresence.get(userid).equals(presence))
            {
                // ignoring - unchanged duplicate event
                log.debug("ignoring presence {} which matches {}", presence, latestPresence.get(userid));
            }
            else if (latestPresence.containsKey(userid) &&
                     presence.getDate().isBefore(latestPresence.get(userid).getDate()))
            {
                // ignoring - older than the last event emitted for this gamer
                log.debug("ignoring presence {} which is before the last update for {}", userid);
            }
            else if (presence.getDate().isBefore(initialTimestamp)) {
                // ignoring - older than the last persisted offset from a previous run of the Connector
                log.debug("ignoring presence {} which is older than initial offset {}", presence, initialTimestamp);
            }
            else {
                log.info("Identified new presence event {} with timestamp {}", presence, presence.getDate());

                // cache to avoid repeating this event
                latestPresence.put(userid, presence);

                // add to the queue waiting to be fetched by Kafka Connect
                presenceItemsCache.add(presence);
            }
        }
    }


    public synchronized List<Presence> getPresences() {
        List<Presence> items = new ArrayList<>();

        while (presenceItemsCache.isEmpty() == false) {
            Presence nextItem = presenceItemsCache.first();

            boolean removed = presenceItemsCache.remove(nextItem);
            if (!removed) {
                log.error("failed to remove item from cache {}", nextItem);
            }
            else {
                items.add(nextItem);
            }
        }

        return items;
    }
}
