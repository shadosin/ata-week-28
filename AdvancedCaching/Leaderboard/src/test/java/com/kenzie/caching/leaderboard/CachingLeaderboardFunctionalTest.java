package com.kenzie.caching.leaderboard;

import com.kenzie.caching.leaderboard.resources.datasource.LeaderboardDao;
import com.kenzie.caching.leaderboard.resources.modules.ClientComponent;
import com.kenzie.caching.leaderboard.resources.modules.DaggerClientComponent;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test can only be run with the RDE workflow. Make sure you have run `rde stack provision` first.
 */
public class CachingLeaderboardFunctionalTest {
    private static final ClientComponent component = DaggerClientComponent.create();
    private static final String TEST_USER = "bestPlayer99";
    private static final long TEST_USER_EXPECTED_HIGH_SCORE = 7362739;

    @Test
    void getScore_UsesCache() {
        // GIVEN
        CacheClient cacheClient = component.buildClient();
        LeaderboardDao leaderboardDao = new LeaderboardDao();
        CachingLeaderboardDao cachingDao = new CachingLeaderboardDao(leaderboardDao, cacheClient);
        cachingDao.getHighScore(TEST_USER);

        // WHEN
        // get the result, and time it
        Instant start = Instant.now();
        long result = cachingDao.getHighScore(TEST_USER);
        Instant end = Instant.now();

        // THEN
        // yield expected result
        assertEquals(TEST_USER_EXPECTED_HIGH_SCORE, result);
        // GIVEN steps successfully pre-cached value, resulting in quick response
        Duration duration = Duration.between(start, end);
        assertTrue(duration.getSeconds() < 5, "getScore does not properly cache previously accessed data");
    }
}
