package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.UpdatePriorityRequest;

import com.nashss.se.beefy.activities.results.UpdatePriorityResult;
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

public class UpdatePriorityActivity {

    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new Update priority activity.
     *
     * @param goalDao          the goal dao
     * @param metricsPublisher the metrics publisher
     */
    @Inject
    public UpdatePriorityActivity(GoalDao goalDao, MetricsPublisher metricsPublisher) {
        this.goalDao = goalDao;
        this.metricsPublisher = metricsPublisher;
    }

    public UpdatePriorityResult handleRequest(final UpdatePriorityRequest request) {
        log.info("Received UpdatePriorityRequest {}", request);

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

        goal.setPriority(request.getPriority());
        Goal savedGoal = goalDao.saveGoal(goal);

        return UpdatePriorityResult.builder()
                .withGoalModel(new ModelConverter().toGoalModel(savedGoal))
                .build();
    }


}
