package com.kenzie.caching.goodreads.activity;

import com.kenzie.caching.goodreads.dao.ReadingGoalDao;
import com.kenzie.caching.goodreads.dao.models.ReadingGoal;

import javax.inject.Inject;

/**
 * Handles requests to set a reading goal for a user at the start of a year.
 */
public class SetGoalActivity {

    private final ReadingGoalDao readingGoalDao;

    /**
     * Constructs an Activity with the given DAOs.
     * @param readingGoalDao The ReadingGoalDao to manage the user's reading goal
     */
    @Inject
    public SetGoalActivity(final ReadingGoalDao readingGoalDao) {
        this.readingGoalDao = readingGoalDao;
    }

    /**
     * Marks the provided book as read for a user.
     * @param userId - the user who has finished the book
     * @param year - the year to set the goal for
     * @param goalNumberOfBooks - the number of books the user wants to read in the year
     *
     * @return the updated reading goal for the user and year
     */
    public ReadingGoal handleRequest(final String userId, final int year, final int goalNumberOfBooks) {
        return readingGoalDao.createGoal(userId, year, goalNumberOfBooks);

    }
}
