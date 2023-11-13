package com.kenzie.caching.leaderboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CacheClientInvalidationTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final int TTL = 100;

    @Mock
    private Jedis jedis;

    @Mock
    private JedisPool jedisPool;

    @InjectMocks
    private CacheClient cacheClient;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void setValue_nullKey_throwsIllegalArgumentException() {
        // GIVEN
        String key = null;

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> cacheClient.setValue(key, TTL, VALUE),
                "Redis does not allow for null keys. " +
                        "So we want to check for null keys and throw an IllegalArgumentException.");
    }

    @Test
    void setValue_givenCacheEntry_storesInRedis() {
        // GIVEN
        when(jedisPool.getResource()).thenReturn(jedis);

        // WHEN
        cacheClient.setValue(KEY, TTL, VALUE);

        // THEN
        verify(jedis).setex(KEY, TTL, VALUE);
    }

    @Test
    void getValue_nullKey_throwsIllegalArgumentException() {
        // GIVEN
        String key = null;

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> cacheClient.getValue(key),
                "Redis does not allow for null keys. " +
                        "So we want to check for null keys and throw an IllegalArgumentException.");

    }

    @Test
    void getValue_cacheHit_getsData() {
        // GIVEN
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.get(KEY)).thenReturn(VALUE);

        // WHEN
        Optional<String> cachedValue = cacheClient.getValue(KEY);

        // THEN
        assertEquals(VALUE, cachedValue.get(),
                "When Jedis returns the value, the cache client should wrap that value in an Optional.");
    }

    @Test
    void getValue_cacheMiss_returnsEmptyOptional() {
        // GIVEN
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.get(KEY)).thenReturn(null);

        // WHEN
        Optional<String> cachedValue = cacheClient.getValue(KEY);

        // THEN
        assertFalse(cachedValue.isPresent(), "On cache misses, we want to return an empty Optional.");
    }

    @Test
    void invalidate_cacheKeyExists_removeFromCache() {
        // GIVEN
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.get(KEY)).thenReturn(VALUE);
        when(jedis.del(KEY)).thenReturn(2L);

        // WHEN
        boolean result = cacheClient.invalidate(KEY);

        // THEN
        assertTrue(result,
                "CacheClient should return true, when trying to invalidate a key present in the cache.");
        verify(jedis).del(KEY);
    }

    @Test
    void invalidate_cacheKeyDoesNotExist_returnFalse() {
        // GIVEN
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.get(KEY)).thenReturn(null);
        when(jedis.del(KEY)).thenReturn(0L);

        // WHEN
        boolean result = cacheClient.invalidate(KEY);

        // THEN
        assertFalse(result,
                "CacheClient should return false, when trying to invalidate a key not in the cache.");
    }

    @Test
    void invalidate_nullKey_throwsIllegalArgumentException() {
        // GIVEN
        String key = null;

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> cacheClient.invalidate(key),
                "Redis does not allow for null keys. " +
                        "So we want to check for null keys and throw an IllegalArgumentException.");

    }
}
