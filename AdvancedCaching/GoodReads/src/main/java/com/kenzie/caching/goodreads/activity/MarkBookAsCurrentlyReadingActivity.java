package com.kenzie.caching.goodreads.activity;

import com.kenzie.caching.goodreads.caching.CachingReadingLogDao;
import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import com.kenzie.caching.goodreads.dao.models.ReadingLog;

import java.time.ZonedDateTime;
import javax.inject.Inject;

/**
 * Handles requests to mark a book as currently reading for a user. This is used when a reader starts a book.
 */
public class MarkBookAsCurrentlyReadingActivity {

    private final CachingReadingLogDao readingLogDao;

    /**
     * Constructs an Activity with the given DAOs.
     * @param readingLogDao The ReadingLogDao to use for updating what a user has read
     */
    @Inject
    public MarkBookAsCurrentlyReadingActivity(final CachingReadingLogDao readingLogDao) {
        this.readingLogDao = readingLogDao;
    }

    /**
     * Mark a book as currently reading for a user. This is used when a reader starts a book.
     * @param userId - the user who has finished the book
     * @param isbn - the id of the book
     * @param timestamp - the time to mark this book as having started being read
     *
     * @return the updated reading log
     */
    public ReadingLog handleRequest(final String userId, final String isbn, final ZonedDateTime timestamp) {
        return readingLogDao.updateReadingProgress(userId, isbn, timestamp, 0, false);

    }
}
