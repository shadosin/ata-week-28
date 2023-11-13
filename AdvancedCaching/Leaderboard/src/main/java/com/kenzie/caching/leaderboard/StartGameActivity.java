package com.kenzie.caching.leaderboard;

import com.kenzie.caching.leaderboard.resources.GameServer;
import com.kenzie.caching.leaderboard.resources.StartGameRequest;
import com.kenzie.caching.leaderboard.resources.StartGameResult;

import javax.inject.Inject;

/**
 * API operation to start a new game for a player.
 */
public class StartGameActivity {

    private final GameServer gameServer;
    private final CachingLeaderboardDao cachingLeaderboardDao;

    @Inject
    public StartGameActivity(GameServer gameServer, CachingLeaderboardDao cachingLeaderboardDao) {
        this.gameServer = gameServer;
        this.cachingLeaderboardDao = cachingLeaderboardDao;
    }

    /**
     * Starts the game on the game server and any other logic we may need for the Start Game API.
     * @param request A request with the username of the player to start a game for.
     * @return The result of starting the game.
     */
    public StartGameResult enact(StartGameRequest request) {
        gameServer.startGame(request.getUsername());
        return new StartGameResult();
    }
}
