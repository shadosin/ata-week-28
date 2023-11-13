package com.kenzie.caching.leaderboard.resources.datasource;

public class Entry {
    private String username;
    private long score;

    /**
     * Constructor.
     * @param username String representing unique username
     * @param score long representing highest recorded score
     */
    public Entry(String username, long score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public long getScore() {
        return score;
    }
}
