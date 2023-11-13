package com.kenzie.caching.goodreads.integration.helper;


import com.kenzie.caching.goodreads.activity.GetGoalProgressActivity;
import com.kenzie.caching.goodreads.activity.MarkBookAsCurrentlyReadingActivity;
import com.kenzie.caching.goodreads.activity.MarkBookAsReadActivity;
import com.kenzie.caching.goodreads.activity.SetGoalActivity;
import com.kenzie.caching.goodreads.activity.UpdateReadingProgressActivity;
import com.kenzie.caching.goodreads.dependency.DaggerServiceComponent;
import com.kenzie.caching.goodreads.dependency.ServiceComponent;

import redis.clients.jedis.JedisPool;

public final class ActivityProvider {
    private static final ServiceComponent DAGGER = DaggerServiceComponent.create();

    private ActivityProvider() {
    }

    public static GetGoalProgressActivity provideGetGoalProgressActivity() {
        return DAGGER.provideGetGoalProgressActivity();
    }

    public static MarkBookAsCurrentlyReadingActivity provideMarkBookAsCurrentlyReadingActivity() {
        return DAGGER.provideMarkBookAsCurrentlyReadingActivity();
    }

    public static MarkBookAsReadActivity provideMarkBookAsReadActivity() {
        return DAGGER.provideMarkBookAsReadActivity();
    }

    public static SetGoalActivity provideSetGoalActivity() {
        return DAGGER.provideSetGoalActivity();
    }

    public static UpdateReadingProgressActivity provideUpdateReadingProgressActivity() {
        return DAGGER.provideUpdateReadingProgressActivity();
    }

    public static JedisPool provideJedisPool() {
        return DAGGER.provideJedisPool();
    }
}