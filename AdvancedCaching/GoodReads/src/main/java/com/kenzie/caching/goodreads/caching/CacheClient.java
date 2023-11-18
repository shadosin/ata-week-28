package com.kenzie.caching.goodreads.caching;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.inject.Inject;
import java.util.Optional;

public class CacheClient {
    private final JedisPool pool;
    @Inject
    public CacheClient(JedisPool jedisPool) {
        this.pool = jedisPool;
    }
    public void setValue(String key, int seconds, String value){
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
        try(Jedis jedis = pool.getResource()){
            jedis.setex(key, seconds, value);
        }
    }
    public Optional<String> getValue(String key){
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
        try(Jedis jedis = pool.getResource()){
            String value = jedis.get(key);
            return Optional.ofNullable(value);
        }
    }
    public void invalidate(String key){
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
        try(Jedis jedis = pool.getResource()){
            jedis.del(key);
        }
    }

}
