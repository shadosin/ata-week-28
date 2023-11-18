package com.kenzie.caching.leaderboard;

import com.kenzie.caching.leaderboard.resources.datasource.Entry;
import com.kenzie.caching.leaderboard.resources.datasource.LeaderboardDao;

import javax.inject.Inject;

public class CachingLeaderboardDao {
    private final LeaderboardDao dataSource;
    private final CacheClient cache;

    /**
     * Constructor.
     *
     * @param dataSource LeaderboardDAO object
     * @param cache      CacheClient object
     */
    @Inject
    public CachingLeaderboardDao(LeaderboardDao dataSource, CacheClient cache) {
        this.dataSource = dataSource;
        this.cache = cache;
    }

    /**
     * Retrieves score associated with the specified user. Should use the cache when possible, but the dataSource object
     * is our source of truth for high scores. The TTL for our high scores should be 5 minutes.
     *
     * PARTICIPANTS: replace return 0 with your implementation of this method.
     *
     * @param username String representing player username
     * @return long representing score associated with username
     */
    public long getHighScore(String username) {
        return cache.getValue(username)
                .map(Long::valueOf)
                .orElseGet(() -> getHighSCoreFromDataBase(username));
    }
    private long getHighSCoreFromDataBase(String username){
        Entry dataValue = dataSource.getEntry(username);
        long highScore = dataValue.getScore();
        cache.setValue(username, 300, String.valueOf(highScore));
        return highScore;
    }
    public void checkHighScore(String username){
        Entry dataStore = dataSource.getEntry(username);
        long storedScore = cache.getValue(username).map(Long::valueOf).orElse(0L);
        long dataScore = dataStore.getScore();
        if(dataScore > storedScore){
            cache.invalidate(username);
            cache.setValue(username, 300, String.valueOf(dataScore));
        }
    }
    public void dumpScore(String username){
        if(cache.getValue(username).isPresent()){
            cache.invalidate(username);
        }
    }
}

