package com.kenzie.caching.leaderboard.resources;

/**
 * Request to start the game.
 */
public class StartGameRequest {
    private final String username;

    public StartGameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
