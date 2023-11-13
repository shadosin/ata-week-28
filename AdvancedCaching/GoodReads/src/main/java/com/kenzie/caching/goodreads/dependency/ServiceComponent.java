package com.kenzie.caching.goodreads.dependency;


import com.kenzie.caching.goodreads.activity.GetGoalProgressActivity;
import com.kenzie.caching.goodreads.activity.MarkBookAsCurrentlyReadingActivity;
import com.kenzie.caching.goodreads.activity.MarkBookAsReadActivity;
import com.kenzie.caching.goodreads.activity.SetGoalActivity;
import com.kenzie.caching.goodreads.activity.UpdateReadingProgressActivity;
import dagger.Component;
import redis.clients.jedis.JedisPool;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {DaoModule.class, CachingModule.class})
public interface ServiceComponent {
    MarkBookAsReadActivity provideMarkBookAsReadActivity();

    UpdateReadingProgressActivity provideUpdateReadingProgressActivity();

    MarkBookAsCurrentlyReadingActivity provideMarkBookAsCurrentlyReadingActivity();

    SetGoalActivity provideSetGoalActivity();

    GetGoalProgressActivity provideGetGoalProgressActivity();

    JedisPool provideJedisPool();
}
