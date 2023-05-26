package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.CreateGoalRequest;
import com.nashss.se.beefy.activities.results.CreateGoalResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;
import com.nashss.se.beefy.utility.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the CreateGoalActivity for Beef's CreateGoal API.
 * <p>
 * This API allows the customer to create a new goal.
 */
public class CreateGoalActivity {

    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;

    /**
     * Instantiates a new CreateGoalActivity object.
     *
     * @param goalDao GoalDao to access the playlists table.
     */
    @Inject
    public CreateGoalActivity(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    /**
     * This method handles the incoming request by persisting a new goal
     * with the provided goal name and user ID from the request.
     * <p>
     * It then returns the newly created goal.
     * <p>
     * If the provided goal name or user ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createGoalRequest request object containing the oal name and user ID
     *                              associated with it
     * @return createGoalResult result object containing the API defined {@link com.nashss.se.beefy.models.GoalModel}
     */
    public CreateGoalResult handleRequest(final CreateGoalRequest request) {
        log.info("Received CreateGoalRequest {}", request);

        Goal newGoal = new Goal();

        newGoal.setGoalId(ServiceUtils.generateGoalId());
        newGoal.setName(request.getName());
        newGoal.setDescription(request.getDescription());
        newGoal.setGoalAmount(request.getGoalAmount();
        newGoal.setPriority(request.getPriority());
        newGoal.setCategory(request.getCategory());
        newGoal.setUserId(request.getUserId());

        goalDao.saveGoal(newGoal);

        GoalModel goalModel = new ModelConverter().toGoalModel(newGoal);
        return CreateGoalResult.builder()
                .withGoalModel(goalModel)
                .build();
    }
}
