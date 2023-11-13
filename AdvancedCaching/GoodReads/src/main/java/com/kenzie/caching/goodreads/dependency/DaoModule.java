package com.kenzie.caching.goodreads.dependency;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.caching.goodreads.DynamoDbClientProvider;
import com.kenzie.caching.goodreads.dao.NonCachingReadingLogDao;
import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Provides DynamoDBMapper instance to DAO classes.
 */
@Module
public class DaoModule {

    /**
     * Creates and returns a DynamoDBMapper instance for the appropriate region.
     * @return a DynamoDBMapper
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_1));
    }

    /**
     * Creates and returns a ReadingLogDao instance.
     * @param readingLogDao a NonCachingReadingLogDao (which implements ReadingLogDao)
     * @return a ReadingLogDao
     */
    @Singleton
    @Provides
    public ReadingLogDao provideReadingLogDao(NonCachingReadingLogDao readingLogDao) {
        return readingLogDao;
    }
}
