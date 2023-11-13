package com.kenzie.caching.leaderboard.resources.modules;

import dagger.Module;
import dagger.Provides;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.inject.Singleton;

@Module
public class CachingModule {
    /**
     * This method provides a JedisPool object used to connect to a Redis cache.
     * @return A JedisPool object
     */
    @Provides
    @Singleton
    public JedisPool provideJedisPool() {
        return new JedisPool(new JedisPoolConfig(), "localhost", 6379, 20000);
    }
}
