package com.kenzie.caching.goodreads.dao.models;

public class ReadingGoalProgress {
    private final ReadingGoal readingGoal;
    private final int numberOfBooksRead;

    public ReadingGoalProgress(ReadingGoal readingGoal, int numberOfBooksRead) {
        this.readingGoal = readingGoal;
        this.numberOfBooksRead = numberOfBooksRead;
    }

    public ReadingGoal getReadingGoal() {
        return readingGoal;
    }

    public int getNumberOfBooksRead() {
        return numberOfBooksRead;
    }

    @Override
    public String toString() {
        return String.format("{ ReadingGoal: %s, numberOfBooksRead: %s}", readingGoal, numberOfBooksRead);
    }
}
