package com.kenzie.caching.goodreads.dao;

import com.kenzie.caching.goodreads.dao.models.ReadingLog;

import java.time.ZonedDateTime;

public interface ReadingLogDao {
    ReadingLog updateReadingProgress(String userId, String isbn, ZonedDateTime timestamp,
                                     int pageNumber, boolean isFinished);

    int getBooksReadInYear(String userId, int year);
}
