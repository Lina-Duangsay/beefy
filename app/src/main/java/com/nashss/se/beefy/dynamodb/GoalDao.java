package com.nashss.se.beefy.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nashss.se.beefy.dynamodb.models.Goal.GOALS_BY_CATEGORY_INDEX;
import static com.nashss.se.beefy.metrics.MetricsConstants.GETGOAL_GOALNOTFOUND_COUNT;
import static com.nashss.se.beefy.metrics.MetricsConstants.GETGOAL_USERNOTFOUND_COUNT;

/**
 * Accesses data for a goal using {@link Goal} to represent the model in DynamoDB.
 */
@Singleton
public class GoalDao {

    private final DynamoDBMapper mapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a GoalDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public GoalDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link Goal} corresponding to the specified UserId and name.
     *
     * @param userId the user's ID
     * @param name the Goal name
     * @return the stored Goal, or null if none was found.
     */
    public Goal getGoal(String goalId) {
        Goal goal = this.mapper.load(Goal.class, goalId);

        if (goalId == null) {
            metricsPublisher.addCount(GETGOAL_GOALNOTFOUND_COUNT, 1);
            throw new GoalNotFoundException("goal id: " + goalId + " did not return any results!");
        }

        metricsPublisher.addCount(GETGOAL_GOALNOTFOUND_COUNT, 0);
        metricsPublisher.addCount(GETGOAL_USERNOTFOUND_COUNT, 0);

        return goal;
    }

    /**
     * Saves (creates or updates) the given goal.
     *
     * @param goal The goal to save
     * @return The Goal object that was saved
     */
    public Goal saveGoal(Goal goal) {
        this.mapper.save(goal);
        return goal;
    }

    /**
     * Deletes the given goal from the table.
     *
     * @param goal The goal to delete
     */
    public void deleteGoal(Goal goal) {
        this.mapper.delete(goal);
    }


    /**
     * GSI for retrieving a goal by category.
     *
     * @param category The category to retrieve
     * @return List of Goals with that category.
     */
    public List<Goal> getGoalByCategory(String category) {
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":category", new AttributeValue().withS(category));
        DynamoDBQueryExpression<Goal> queryExpression = new DynamoDBQueryExpression<Goal>()
                .withIndexName(GOALS_BY_CATEGORY_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("category = :category")
                .withExpressionAttributeValues(valueMap);

        return mapper.query(Goal.class, queryExpression);
    }

}
