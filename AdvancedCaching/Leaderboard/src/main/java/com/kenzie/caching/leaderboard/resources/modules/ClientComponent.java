package com.kenzie.caching.leaderboard.resources.modules;

import com.kenzie.caching.leaderboard.CacheClient;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = CachingModule.class)
public interface ClientComponent {
    /**
     * Method created by Dagger.
     * @return CacheClient object.
     */
    CacheClient buildClient();
}
