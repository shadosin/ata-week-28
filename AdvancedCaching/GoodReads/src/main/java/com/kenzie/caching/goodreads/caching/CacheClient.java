package com.kenzie.caching.goodreads.caching;

import redis.clients.jedis.JedisPool;

import javax.inject.Inject;

public class CacheClient {
    @Inject
    public CacheClient(JedisPool jedisPool) {

    }
}
