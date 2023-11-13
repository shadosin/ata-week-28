package com.kenzie.caching.goodreads.dao.models;



import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.kenzie.caching.goodreads.converter.ZonedDateTimeConverter;

import java.time.ZonedDateTime;

/**
 * Represents a group membership.
 */
@DynamoDBTable(tableName = "Caching-ReadingLogs")
public class ReadingLog {

    public static final String USER_ID_ISBN_GSI = "userId-isbn";
    public static final String USER_ID_STATUS_GSI = "userId-status";

    private String userId;
    private ZonedDateTime date;
    private String isbn;
    private ReadingStatus readingStatus;
    private int pageNumberRead;

    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {USER_ID_ISBN_GSI, USER_ID_STATUS_GSI})
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    @DynamoDBRangeKey(attributeName = "date")
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = USER_ID_ISBN_GSI)
    @DynamoDBAttribute(attributeName = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = USER_ID_STATUS_GSI)
    @DynamoDBAttribute(attributeName = "status")
    public ReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    @DynamoDBAttribute(attributeName = "page")
    public int getPageNumberRead() {
        return pageNumberRead;
    }

    public void setPageNumberRead(int pageNumberRead) {
        this.pageNumberRead = pageNumberRead;
    }
}
