package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.GetGoalRequest;
import com.nashss.se.beefy.activities.results.GetGoalResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetGoalByNameActivity for the Beef's GetGoalByNameActivity API.
 *
 * This API allows the customer to get one of their saved goals.
 */
public class GetGoalActivity {
    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;


    /**
     * Instantiates a new GetGoalByNameActivity object.
     *
     * @param goalDao GoalDao to access the goal table.
     */
    @Inject
    public GetGoalActivity(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    /**
     * This method handles the incoming request by retrieving the goal from the database.
     * <p>
     * It then returns the goal.
     * <p>
     * If the playlist does not exist, this should throw a GoalNotFoundException.
     *
     * @param request request object containing the userId and goal name
     * @return getGoalByNameResult result object containing the API defined {@link com.nashss.se.beefy.models.GoalModel}
     */
    public GetGoalResult handleRequest(final GetGoalRequest request) {
        log.info("Received GetGoalByNameRequest {}", request);

        String requestGoalId = request.getGoalId();

        Goal goal = goalDao.getGoal(requestGoalId);
        GoalModel goalModel = new ModelConverter().toGoalModel(goal);

        return GetGoalResult.builder()
                .withGoal(goalModel)
                .build();
    }

}
