package com.kenzie.caching.goodreads.integration;

import com.kenzie.caching.goodreads.activity.GetGoalProgressActivity;
import com.kenzie.caching.goodreads.dao.models.ReadingGoalProgress;
import com.kenzie.caching.goodreads.integration.helper.ActivityProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Phase2Test {
    private static final String USER_ID = "8f4d9aa0-7180-4f23-8a59-8cc5a0786b44";

    private GetGoalProgressActivity getGoalProgressActivity;
    private JedisPool jedisPool;

    @BeforeEach
    public void setup() {
        getGoalProgressActivity = ActivityProvider.provideGetGoalProgressActivity();
        jedisPool = ActivityProvider.provideJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        }
    }

    @Test
    public void getGoalProgress_cacheMiss_takesAWhile() {
        // GIVEN
        int year = 2019;

        // WHEN
        Instant start = Instant.now();
        ReadingGoalProgress progress = getGoalProgressActivity.handleRequest(USER_ID, year);
        Instant end = Instant.now();

        // THEN
        assertTrue(Duration.between(start, end).compareTo(Duration.ofSeconds(5)) > -1,
                String.format("Had not requested user ID, %s, and year, %s, previously. " +
                        "This should have been a cache miss and taken a while to complete.", USER_ID, year));
    }

    @Test
    public void getGoalProgress_cacheHit_quickAndEntryInCache() {
        // GIVEN
        int year = 2020;
        getGoalProgressActivity.handleRequest(USER_ID, year);

        // WHEN
        Instant start = Instant.now();
        ReadingGoalProgress progress = getGoalProgressActivity.handleRequest(USER_ID, year);
        Instant end = Instant.now();

        // THEN
        assertEquals(-1, Duration.between(start, end).compareTo(Duration.ofSeconds(5)),
                String.format("Had previously requested user ID, %s, and year, %s. " +
                        "This should have been a cache hit and completed quickly", USER_ID, year));
        try (Jedis jedis = jedisPool.getResource()) {
            assertEquals(1, jedis.dbSize(),
                    "There should have been an item in the cache to cause this cache hit.");
        }

    }

    @Test
    public void getGoalProgress_TwoEntriesMadeSameUserDiffYears(){
        int year1 = 2019;
        int year2 = 2020;

        ReadingGoalProgress progress1 = getGoalProgressActivity.handleRequest(USER_ID, year1);
        ReadingGoalProgress progress2 = getGoalProgressActivity.handleRequest(USER_ID, year2);

        try (Jedis jedis = jedisPool.getResource()) {
            assertEquals(2, jedis.dbSize(),
                    "There should be 2 items in the cache, one for each year for the user.");
        }

    }

    @Test
    public void getGoalProgress_TwoEntriesMadeDiffUsersSameYear(){
        String UserId2 = "425c0e68-717c-49ef-9405-259453a5180d";
        int year = 2020;

        ReadingGoalProgress progress1 = getGoalProgressActivity.handleRequest(USER_ID, year);
        ReadingGoalProgress progress2 = getGoalProgressActivity.handleRequest(UserId2, year);

        try (Jedis jedis = jedisPool.getResource()) {
            assertEquals(2, jedis.dbSize(),
                    "There should be 2 items in the cache, one for each user.");
        }

    }


}
