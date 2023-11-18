package com.kenzie.caching.goodreads.activity;

import com.kenzie.caching.goodreads.caching.CachingReadingLogDao;
import com.kenzie.caching.goodreads.dao.ReadingGoalDao;
import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import com.kenzie.caching.goodreads.dao.models.ReadingLog;

import java.time.ZonedDateTime;
import javax.inject.Inject;

/**
 * Handles requests to update the reading progress for a user. This allows the user to periodically log how far in the
 * book they are while they read.
 */
public class UpdateReadingProgressActivity {

    private final CachingReadingLogDao readingLogDao;
    private final ReadingGoalDao readingGoalDao;

    /**
     * Constructs an Activity with the given DAOs.
     * @param readingLogDao The ReadingLogDao to use for updating what a user has read
     * @param readingGoalDao The ReadingGoalDao to manage the user's reading goal
     */
    @Inject
    public UpdateReadingProgressActivity(final CachingReadingLogDao readingLogDao, final ReadingGoalDao readingGoalDao) {
        this.readingLogDao = readingLogDao;
        this.readingGoalDao = readingGoalDao;
    }

    /**
     * Updates the reading progress for a user. This allows the user to periodically log how far in the book they are
     * while they read.
     * @param userId - the user who has finished the book
     * @param isbn - the id of the book
     * @param timestamp - the time to mark this update as occurring
     * @param numberPagesInBook - the total number of pages in the book
     * @param isFinished - whether or not the user is done with the book
     *
     * @return the updated reading log for the user and book
     */
    public ReadingLog handleRequest(final String userId,
                                    final String isbn,
                                    final ZonedDateTime timestamp,
                                    final int numberPagesInBook,
                                    final boolean isFinished) {
        return readingLogDao.updateReadingProgress(userId, isbn, timestamp, numberPagesInBook, isFinished);

    }
}
