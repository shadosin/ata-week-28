package com.kenzie.caching.goodreads.caching;


import com.kenzie.caching.goodreads.dao.NonCachingReadingLogDao;
import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import com.kenzie.caching.goodreads.dao.models.ReadingLog;

import java.time.ZonedDateTime;
import java.util.Optional;
import javax.inject.Inject;

public class CachingReadingLogDao implements ReadingLogDao {

    private CacheClient cacheClient;
    private NonCachingReadingLogDao nonCache;
    private ReadingLogDao readingDao;


    @Inject
    public CachingReadingLogDao(CacheClient cacheClient, NonCachingReadingLogDao nonCache) {
        this.cacheClient = cacheClient;
        this.nonCache = nonCache;
    }

    @Override
    public ReadingLog updateReadingProgress(String userId, String isbn, ZonedDateTime timestamp,
                                            int pageNumber, boolean isFinished) {
        ReadingLog readingLog;

        if(isFinished){
            readingLog = nonCache.updateReadingProgress(userId, isbn, timestamp, pageNumber, true);
            invalidateCacheForUser(userId, timestamp.getYear());
        }else{
            readingLog = nonCache.updateReadingProgress(userId, isbn, timestamp, pageNumber, false);
        }
        return readingLog;
    }

    @Override
    public int getBooksReadInYear(String userId, int year) {
        String key = cacheKey(userId, year);
        Optional<String> cachedValue = cacheClient.getValue(key);
        if(cachedValue.isPresent()){
            return Integer.parseInt(cachedValue.get());
        }else{
           int booksRead = nonCache.getBooksReadInYear(userId, year);
           cacheClient.setValue(key, 3600, String.valueOf(booksRead));
           return booksRead;
        }
    }

    public String cacheKey(String userId, int year){
        return ("books-read::" + userId + "::" + year);
    }
    public void invalidateCacheForUser(String userId, int year){
        String key = cacheKey(userId, year);
        cacheClient.invalidate(key);
    }
}

