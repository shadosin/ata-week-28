package com.kenzie.caching.goodreads.integration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.fail;

public class Phase0Test {
    private static final String TABLE_NAME_PREFIX = "Caching-";
    private static final String READING_GOALS_TABLE_NAME = TABLE_NAME_PREFIX + "ReadingGoals";
    private static final String READING_LOGS_TABLE_NAME = TABLE_NAME_PREFIX + "ReadingLogs";

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_EAST_1).build());

    @ParameterizedTest
    @ValueSource(strings = {READING_GOALS_TABLE_NAME, READING_LOGS_TABLE_NAME,})
    void expectedTable_exists(String tableName) {
        assertTableExists(tableName);
    }

    private void assertTableExists(String tableName) {
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(tableName)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", tableName));
    }
}
