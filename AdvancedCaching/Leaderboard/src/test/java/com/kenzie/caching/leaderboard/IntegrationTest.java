package com.kenzie.caching.leaderboard;

import com.kenzie.caching.leaderboard.resources.GameServer;
import com.kenzie.caching.leaderboard.resources.StartGameRequest;
import com.kenzie.caching.leaderboard.resources.datasource.LeaderboardDao;
import com.kenzie.caching.leaderboard.resources.modules.ClientComponent;
import com.kenzie.caching.leaderboard.resources.modules.DaggerClientComponent;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

public class IntegrationTest {
    private static final ClientComponent component = DaggerClientComponent.create();
    private static final String USERNAME = "okayPlayer92";

    @Test
    void invalidate_whenKeyExists_deletesKey() {
        // GIVEN
        // preload the cache
        CacheClient cacheClient = component.buildClient();
        CachingLeaderboardDao cachingLeaderboardDao = new CachingLeaderboardDao(new LeaderboardDao(), cacheClient);
        cachingLeaderboardDao.getHighScore(USERNAME);
        // form the new request to start game
        StartGameActivity activity = new StartGameActivity(new GameServer(), cachingLeaderboardDao);
        StartGameRequest request = new StartGameRequest(USERNAME);

        // WHEN
        activity.enact(request);

        // THEN subsequent request for high score should not be cached and take a while to return
        Instant start = Instant.now();
        cachingLeaderboardDao.getHighScore(USERNAME);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        assertTrue(duration.getSeconds() >= 5,
                    String.format("Enacting the StartGameActivity should invalidate entry for username " +
                                      "[%s] in the cache.",
                                  USERNAME));
    }

    @Test
    public void startGame_InvalidatesInCorrectOrder(){
        // GIVEN
        // preload the cache
        CacheClient cacheClient = spy(component.buildClient());
        CachingLeaderboardDao cachingLeaderboardDao = new CachingLeaderboardDao(new LeaderboardDao(), cacheClient);
        cachingLeaderboardDao.getHighScore(USERNAME);
        // form the new request to start game
        GameServer gameServer = spy(new GameServer());
        StartGameActivity activity = new StartGameActivity(gameServer, cachingLeaderboardDao);
        StartGameRequest request = new StartGameRequest(USERNAME);

        //spy cacheClient and gameServer so we can see what they're doing

        activity.enact(request);

        InOrder orderVerifier = Mockito.inOrder(cacheClient,gameServer);

        orderVerifier.verify(cacheClient).invalidate(any());
        orderVerifier.verify(gameServer).startGame(USERNAME);
    }
}
