package com.kenzie.caching.goodreads.dao;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.kenzie.caching.goodreads.dao.models.ReadingLog;
import com.kenzie.caching.goodreads.dao.models.ReadingStatus;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Manages access to reading logs.
 */
public class NonCachingReadingLogDao implements ReadingLogDao {

    private final DynamoDBMapper mapper;

    /**
     * Creates a NonCachingReadingLogDao with the given DDB mapper.
     * @param mapper DynamoDBMapper
     */
    @Inject
    public NonCachingReadingLogDao(final DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Updates reading progress for a user on a book.
     * @param userId the ID of the user
     * @param isbn an identifier for books
     * @param timestamp the time this updated progress took place
     * @param pageNumber the current page number the user had reached
     * @param isFinished whether the user has finished the book
     * @return The ReadingLog entry
     */
    @Override
    public ReadingLog updateReadingProgress(String userId, String isbn, ZonedDateTime timestamp,
                                            int pageNumber, boolean isFinished) {
        ReadingLog readingLog = new ReadingLog();
        readingLog.setUserId(userId);
        readingLog.setIsbn(isbn);
        readingLog.setDate(timestamp);
        readingLog.setPageNumberRead(pageNumber);

        ReadingStatus status = isFinished ? ReadingStatus.READ : ReadingStatus.CURRENTLY_READING;
        readingLog.setReadingStatus(status);

        mapper.save(readingLog);

        return readingLog;
    }

    /**
     * Determine the number of books a user has read in a year.
     * @param userId The id of the user to find the book number for.
     * @param year The year to count books read in.
     * @return The number of books read by the user in the year.
     */
    @Override
    public int getBooksReadInYear(String userId, int year) {
        ReadingLog readingLog = new ReadingLog();
        readingLog.setUserId(userId);

        Condition beginsWithYear = new Condition()
                .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(Integer.toString(year)));

        Condition readStatus = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(ReadingStatus.READ.toString()));

        DynamoDBQueryExpression<ReadingLog> queryExpression = new DynamoDBQueryExpression<ReadingLog>()
                .withHashKeyValues(readingLog)
                .withRangeKeyCondition("date", beginsWithYear)
                .withQueryFilterEntry("status", readStatus);

        DynamoDBMapperConfig eagerLoading = DynamoDBMapperConfig.builder()
                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
                .build();


        PaginatedQueryList<ReadingLog> readingLogs = mapper.query(ReadingLog.class, queryExpression, eagerLoading);

        try {
            // Here to simulate a long expensive calculation
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // Go ahead and return the result
        }
        return readingLogs.size();
    }
}
