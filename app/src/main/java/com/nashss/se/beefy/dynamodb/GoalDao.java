package com.nashss.se.beefy.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        if (goal == null) {
            if (goalId == null) {
                metricsPublisher.addCount(GETGOAL_GOALNOTFOUND_COUNT, 1);
                throw new GoalNotFoundException("goal id: " + goalId + " did not return any results!");
            }
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

}
