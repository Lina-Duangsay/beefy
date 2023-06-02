package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.UpdateGoalAmountActivity;
import com.nashss.se.beefy.activities.requests.UpdateGoalAmountRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalAmountResult;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.InvalidAmountException;
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

public class UpdateGoalAmountActivityTest {
    @Mock
    private GoalDao goalDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateGoalAmountActivity updateGoalAmountActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateGoalAmountActivity = new UpdateGoalAmountActivity(goalDao, metricsPublisher);
    }

    @Test
    public void handleRequest_validRequest_updatesGoalAmount() {
        // GIVEN
        String goalId = "1589";
        String userId = "selene";
        double currentAmount = 200.00;
        double newAmount = 250.00;

        UpdateGoalAmountRequest request = UpdateGoalAmountRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withAmount(newAmount)
                .build();

        Goal initialGoal = new Goal();
        initialGoal.setUserId(userId);
        initialGoal.setGoalAmount(currentAmount);
        initialGoal.setGoalId(goalId);

        when(goalDao.getGoal(goalId)).thenReturn(initialGoal);
        when(goalDao.saveGoal(initialGoal)).thenReturn(initialGoal);

        // WHEN
        UpdateGoalAmountResult result = updateGoalAmountActivity.handleRequest(request);

        // THEN
        assertEquals(goalId, result.getModel().getGoalId());
        assertEquals(userId, result.getModel().getUserId());
        assertEquals(newAmount, result.getModel().getGoalAmount());
    }


    @Test
    public void handleRequest_invalidAmount_throwsInvalidAmountException() {
    // GIVEN
        String goalId = "1589";
        String userId = "selene";
        double amount = -1.0;

        UpdateGoalAmountRequest request = UpdateGoalAmountRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withAmount(amount)
                .build();

        Goal newGoal = new Goal();
        newGoal.setGoalId(goalId);
        newGoal.setUserId(userId);
        newGoal.setGoalAmount(amount);

        when(goalDao.getGoal(goalId)).thenReturn(newGoal);

        // WHEN + THEN
        try {
            updateGoalAmountActivity.handleRequest(request);
            fail("Expected InvalidAmountException to be thrown");
        } catch (InvalidAmountException e) {
            verify(metricsPublisher).addCount(MetricsConstants.UPDATEGOALAMOUNT_AMOUNTINVALID_COUNT, 1);
        }
    }
}
