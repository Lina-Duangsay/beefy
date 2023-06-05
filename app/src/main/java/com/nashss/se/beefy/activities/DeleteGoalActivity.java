package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.DeleteGoalRequest;
import com.nashss.se.beefy.activities.requests.GetGoalRequest;
import com.nashss.se.beefy.activities.results.DeleteGoalResult;
import com.nashss.se.beefy.activities.results.GetGoalResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteGoalActivity {

    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;

    @Inject
    public DeleteGoalActivity(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    public DeleteGoalResult handleRequest(final DeleteGoalRequest request) {
        log.info("Received DeleteGoalRequest {}", request);

        String requestGoalId = request.getGoalId();
        String user = request.getUserId();

        Goal goal = goalDao.getGoal(requestGoalId);
        goalDao.deleteGoal(goal);
        GoalModel goalModel = new ModelConverter().toGoalModel(goal);

        return DeleteGoalResult.builder()
                .withGoalModel(goalModel)
                .build();
    }
}
