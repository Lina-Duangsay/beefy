package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.UpdateGoalAmountRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalAmountResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateGoalAmountActivity {
    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Implementation of the UpdateGoalAmountActivity for the BeefyService's UpdateGoalAmount API.
     *
     * This API allows the customer to update their saved goal's amount information.
     */
    public UpdateGoalAmountActivity(GoalDao goalDao, MetricsPublisher metricsPublisher) {
        this.goalDao = goalDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Instantiates a new UpdateGoalAmountActivity object.
     *
     * @param goalDao GoalDAo to access the goal table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdatePlaylistActivity(GoalDao goalDao, MetricsPublisher metricsPublisher) {
        //super(UpdatePlaylistRequest.class);
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
     * @param updateGoalAmountRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return updateGoalAmountResult result object containing the API defined {@link com.nashss.se.beefy.models.GoalModel}
     */
    public UpdateGoalAmountResult handleRequest(final UpdateGoalAmountRequest request) {
        log.info("Received UpdateGoalAmountRequest {}", request);

        Goal goal = goalDao.getGoal(request.getGoalId());

        if (!goal.getGoalId().equals(request.getGoalId())) {
            publishExceptionMetrics(false, true);
            throw new GoalNotFoundException("You do not have a goal with this ID!");
        }

        goal.setGoalAmount(request.getAmount());
        savedGoal = goalDao.saveGoal(goal);

        publishExceptionMetrics(false, false);
        return UpdateGoalAmountResult.builder()
                .withGoal(new ModelConverter().toGoalModel(savedGoal))
                .build();
    }
}
