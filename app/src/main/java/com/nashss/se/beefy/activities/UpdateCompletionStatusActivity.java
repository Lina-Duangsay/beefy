package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.UpdateCompletionStatusRequest;
import com.nashss.se.beefy.activities.results.UpdateCompletionStatusResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.InvalidDescriptionException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import static com.nashss.se.beefy.metrics.MetricsConstants.*;

/**
 * Implementation of the UpdateGoalAmountActivity for the BeefyService's UpdateGoalAmount API.
 *
 * This API allows the customer to update their saved goal's amount information.
 */
public class UpdateCompletionStatusActivity {
    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateCompletionStatusActivity object.
     *
     * @param goalDao GoalDAo to access the goal table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdateCompletionStatusActivity(GoalDao goalDao, MetricsPublisher metricsPublisher) {
        this.goalDao = goalDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the goal, updating its amount,
     * and persisting the goal.
     * <p>
     * It then returns the updated goal.
     * <p>
     * If the goal does not exist, this should throw a GoalNotFoundException.
     *
     * @param request request object containing the goal ID, goal amount, and customer ID
     *                              associated with it
     * @return UpdateCompletionStatusResult result object containing the API defined {@link UpdateCompletionStatusResult}
     */
    public UpdateCompletionStatusResult handleRequest(final UpdateCompletionStatusRequest request) {
        log.info("Received UpdateCompletionStatusRequest {}", request);

        Goal goal = goalDao.getGoal(request.getGoalId());

        if (goal == null) {
            throw new GoalNotFoundException();
        }

        if (!goal.getGoalId().equals(request.getGoalId())) {
            metricsPublisher.addCount(GETGOAL_GOALNOTFOUND_COUNT, 1);
            throw new GoalNotFoundException("You do not have a goal with this ID!");
        }

        if (!goal.getUserId().equals(request.getUserId())) {
            metricsPublisher.addCount(GETGOAL_USERNOTFOUND_COUNT, 1);
            throw new UserNotFoundException("Your user info was not found!");
        }

        if (goal.getCompletionStatus() == null) {
            throw new InvalidDescriptionException("You cannot update the status to be null!");
        }

        goal.setCompletionStatus(request.getCompletionStatus());
        Goal savedGoal = goalDao.saveGoal(goal);

        return UpdateCompletionStatusResult.builder()
                .withGoalModel(new ModelConverter().toGoalModel(savedGoal))
                .build();
    }
}
