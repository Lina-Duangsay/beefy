package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.UpdateGoalAmountActivity;
import com.nashss.se.beefy.activities.UpdateGoalDescriptionActivity;
import com.nashss.se.beefy.activities.requests.UpdateGoalAmountRequest;
import com.nashss.se.beefy.activities.requests.UpdateGoalDescriptionRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalAmountResult;
import com.nashss.se.beefy.activities.results.UpdateGoalDescriptionResult;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.InvalidAmountException;
import com.nashss.se.beefy.exceptions.InvalidDescriptionException;
import com.nashss.se.beefy.metrics.MetricsConstants;
import com.nashss.se.beefy.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateGoalDescriptionActivityTest {

    @Mock
    private GoalDao goalDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateGoalDescriptionActivity updateGoalDescriptionActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateGoalDescriptionActivity = new UpdateGoalDescriptionActivity(goalDao, metricsPublisher);
    }

    @Test
    public void handleRequest_validRequest_updatesDescription() {
        String goalId = "1689";
        String userId = "selene";
        String oldDescription = "need to fuel my obsession for V";
        String newDescription = "need to fuel my new obsession for joshua";

        UpdateGoalDescriptionRequest request = UpdateGoalDescriptionRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withDescription(newDescription)
                .build();

        Goal initialGoal = new Goal();
        initialGoal.setUserId(userId);
        initialGoal.setDescription(oldDescription);
        initialGoal.setGoalId(goalId);

        when(goalDao.getGoal(goalId)).thenReturn(initialGoal);
        when(goalDao.saveGoal(initialGoal)).thenReturn(initialGoal);

        UpdateGoalDescriptionResult result = updateGoalDescriptionActivity.handleRequest(request);

        assertEquals(goalId, result.getModel().getGoalId());
        assertEquals(userId, result.getModel().getUserId());
        assertEquals(newDescription, result.getModel().getDescription());
    }

    @Test
    public void handleRequest_nullDescriptionProvided_throwsInvalidDescriptionException() {
        String goalId = "1589";
        String userId = "selene";
        String badDescription = null;

        UpdateGoalDescriptionRequest request = UpdateGoalDescriptionRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withDescription(badDescription)
                .build();

        Goal newGoal = new Goal();
        newGoal.setGoalId(goalId);
        newGoal.setUserId(userId);
        newGoal.setDescription(badDescription);

        when(goalDao.getGoal(goalId)).thenReturn(newGoal);

        try {
            updateGoalDescriptionActivity.handleRequest(request);
            fail("Expected InvalidDescriptionException to be thrown");
        } catch (InvalidDescriptionException e) {
            verify(metricsPublisher).addCount(MetricsConstants.UPDATEGOALDESCRIPTION_DESCRIPTIONINVALID_COUNT, 1);
        }
    }
}
