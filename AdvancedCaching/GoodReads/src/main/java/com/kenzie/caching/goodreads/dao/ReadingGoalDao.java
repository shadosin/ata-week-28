package com.kenzie.caching.goodreads.dao;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.caching.goodreads.dao.exception.GoalNotFoundException;
import com.kenzie.caching.goodreads.dao.models.ReadingGoal;

import javax.inject.Inject;

/**
 * Manages access to reading goals.
 */
public class ReadingGoalDao {

    private final DynamoDBMapper mapper;

    /**
     * Creates a ReadingGoalDao with the given DDB mapper.
     * @param mapper DynamoDBMapper
     */
    @Inject
    public ReadingGoalDao(final DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Creates a reading goal for the provided user for the provided year.
     * @param userId the user's ID
     * @param year the year to set the goal for
     * @param goal the number of books to set as the goal
     * @return the persisted group
     */
    public ReadingGoal createGoal(String userId, int year, int goal) {
        ReadingGoal readingGoal = new ReadingGoal();
        readingGoal.setUserId(userId);
        readingGoal.setYear(year);
        readingGoal.setGoal(goal);

        mapper.save(readingGoal);

        return readingGoal;
    }

    /**
     * Retrieve the goal from the database for the provided user in the provided year. Throws a GoalNotFoundException
     * if no goal is found.
     * @param userId the id of the user
     * @param year the year to get the goal for
     * @return the user's reading goal for that year
     */
    public ReadingGoal getGoal(String userId, int year) {
        ReadingGoal readingGoal = new ReadingGoal();
        readingGoal.setUserId(userId);
        readingGoal.setYear(year);

        readingGoal = mapper.load(readingGoal);
        if (readingGoal == null) {
            throw new GoalNotFoundException(String.format("No goal was found for userId %s in year %s.", userId, year));
        }

        return readingGoal;
    }
}
