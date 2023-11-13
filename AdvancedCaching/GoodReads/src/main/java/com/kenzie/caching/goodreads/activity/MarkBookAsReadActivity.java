package com.kenzie.caching.goodreads.activity;

import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import com.kenzie.caching.goodreads.dao.models.ReadingLog;

import java.time.ZonedDateTime;
import javax.inject.Inject;

/**
 * Handles requests to mark a book as read for a user.
 */
public class MarkBookAsReadActivity {

    private final ReadingLogDao readingLogDao;

    /**
     * Constructs an Activity with the given DAOs.
     * @param readingLogDao The ReadingLogDao to use for updating what a user has read
     */
    @Inject
    public MarkBookAsReadActivity(final ReadingLogDao readingLogDao) {
        this.readingLogDao = readingLogDao;
    }

    /**
     * Marks the provided book as read for a user.
     * @param userId - the user who has finished the book
     * @param isbn - the id of the book
     * @param timestamp - the the time to mark this book as having completed being read
     * @param numberPagesInBook - the total number of pages in the book
     *
     * @return the updated reading log for the book
     */
    public ReadingLog handleRequest(final String userId, final String isbn, final ZonedDateTime timestamp,
                                    final int numberPagesInBook) {
        return readingLogDao.updateReadingProgress(userId, isbn, timestamp, numberPagesInBook, true);

    }
}
