package com.kenzie.caching.leaderboard;



import com.kenzie.caching.leaderboard.resources.datasource.Entry;
import com.kenzie.caching.leaderboard.resources.datasource.LeaderboardDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CachingLeaderboardDaoTest {
    private static final String USERNAME = "bestPlayer99";
    private static final long HIGH_SCORE = 7362739;

    @Mock
    private LeaderboardDao leaderboardDao;

    @Mock
    private CacheClient cacheClient;

    @InjectMocks
    private CachingLeaderboardDao cachingLeaderboardDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void getHighScore_cacheHit_returnsCachedData() {
        // GIVEN
        when(cacheClient.getValue(matchSubstring(USERNAME))).thenReturn(Optional.of(Long.toString(HIGH_SCORE)));

        // WHEN
        long actualScore = cachingLeaderboardDao.getHighScore(USERNAME);

        // THEN
        assertEquals(HIGH_SCORE, actualScore, "getScore does not properly retrieve data from the data source");
        verifyZeroInteractions(leaderboardDao);
    }

    @Test
    void getHighScore_cacheMiss_returnsDataFrom() {
        // GIVEN
        when(cacheClient.getValue(matchSubstring(USERNAME))).thenReturn(Optional.empty());
        when(leaderboardDao.getEntry(USERNAME)).thenReturn(new Entry(USERNAME, HIGH_SCORE));

        // WHEN
        long actualScore = cachingLeaderboardDao.getHighScore(USERNAME);

        // THEN
        assertEquals(HIGH_SCORE, actualScore, "getScore does not properly retrieve data from the data source");
        verify(cacheClient).setValue(matchSubstring(USERNAME), eq(5 * 60), eq(Long.toString(HIGH_SCORE)));
    }

    private String matchSubstring(String substring) {
        return Mockito.matches(String.format(".*%s.*", substring));
    }
}
