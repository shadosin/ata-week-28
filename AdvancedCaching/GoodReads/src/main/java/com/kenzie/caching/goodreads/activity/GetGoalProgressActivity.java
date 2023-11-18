package com.kenzie.caching.goodreads.activity;

import com.kenzie.caching.goodreads.caching.CachingReadingLogDao;
import com.kenzie.caching.goodreads.dao.ReadingGoalDao;
import com.kenzie.caching.goodreads.dao.ReadingLogDao;
import com.kenzie.caching.goodreads.dao.models.ReadingGoal;
import com.kenzie.caching.goodreads.dao.models.ReadingGoalProgress;

import javax.inject.Inject;

/**
 * Handles requests to get the updated reading goal progress for a user.
 */
public class GetGoalProgressActivity {

    private final ReadingGoalDao readingGoalDao;
    private final CachingReadingLogDao cachingReadingLogDao;

    /**
     * Constructs an Activity with the given DAOs.
     * @param readingLogDao The ReadingLogDao to use for updating what a user has read
     * @param readingGoalDao The ReadingGoalDao to manage the user's reading goal
     */
    @Inject
    public GetGoalProgressActivity( final CachingReadingLogDao cachingReadingLogDao, final ReadingGoalDao readingGoalDao) {
        this.cachingReadingLogDao = cachingReadingLogDao;
        this.readingGoalDao = readingGoalDao;

    }

    /**
     * Get the updated reading goal progress for a user.
     * @param userId - the user
     * @param year - the year to see how they are doing on their goal.
     *
     * @return progress data for given user and year
     */
    public ReadingGoalProgress handleRequest(final String userId, final int year) {
        ReadingGoal readingGoal = readingGoalDao.getGoal(userId, year);
        int books = cachingReadingLogDao.getBooksReadInYear(userId, year);
        return new ReadingGoalProgress(readingGoal, books);
    }
}
