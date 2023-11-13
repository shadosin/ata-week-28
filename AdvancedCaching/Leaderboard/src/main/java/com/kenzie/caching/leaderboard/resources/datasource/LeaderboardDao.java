package com.kenzie.caching.leaderboard.resources.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LeaderboardDao {
    private Map<String, Entry> entries;

    /**
     * Constructor. Sets up a dummy data source for testing.
     */
    public LeaderboardDao() {
        entries = new HashMap<String, Entry>();
        entries.put("bestPlayer99", new Entry("bestPlayer99", 7362739));
        entries.put("okayPlayer92", new Entry("okayPlayer92", 6623543));
        entries.put("poorPlayer95", new Entry("poorPlayer95", 5534233));
    }

    /**
     * Simulates a call to a data source. Returns selected Entry object after a 5-second delay.
     * @param username String representing name of Entry to be retrieved
     * @return Entry object or null
     */
    public Entry getEntry(String username) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // Return the value early
        }
        return entries.getOrDefault(username, null);
    }
}
