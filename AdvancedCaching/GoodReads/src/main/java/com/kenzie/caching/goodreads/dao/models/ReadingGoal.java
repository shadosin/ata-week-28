package com.kenzie.caching.goodreads.dao.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents a group.
 */
@DynamoDBTable(tableName = "Caching-ReadingGoals")
public class ReadingGoal {

    private String userId;
    private int year;
    private int goal;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBRangeKey(attributeName = "year")
    public int getYear() {
        return year;
    }

    @DynamoDBAttribute(attributeName = "goal")
    public int getGoal() {
        return goal;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return String.format("{userId: %s, year: %n, goal: %n}", userId, year, goal);
    }
}
