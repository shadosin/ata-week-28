# Week 28

### Run this command to run a local Redis Cluster:

Make sure you have docker installed and running.

```
./runLocalRedis.sh
```

## Advanced Caching - Leaderboard

### Run these commands to execute the completion tests:

```
./gradlew caching-leaderboard-phase1test
./gradlew caching-leaderboard-phase2test
```

## Advanced Caching - GoodReads

### Run this command to create the DynamoDB Tables:

```
aws cloudformation create-stack --stack-name goal-table --template-body file://AdvancedCaching/GoodReads/goal_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --stack-name reading-log-table --template-body file://AdvancedCaching/GoodReads/reading_log_table.yaml --capabilities CAPABILITY_IAM
```

### Run this command to execute the completion tests:

```
./gradlew caching-goodreads-phase0
./gradlew caching-goodreads-phase2
./gradlew caching-goodreads-phase4
```
