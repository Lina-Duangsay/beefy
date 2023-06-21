package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.CreateGoalActivity;
import com.nashss.se.beefy.activities.requests.CreateGoalRequest;
import com.nashss.se.beefy.activities.results.CreateGoalResult;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateGoalActivityTest {
    @Mock
    private GoalDao goalDao;

    private CreateGoalActivity createGoalActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createGoalActivity = new CreateGoalActivity(goalDao);
    }

    @Test
    public void handleRequest_createsAndSavesGoal() {
        String goalName = "lululemon bbl jacket";
        String userID = "gymlover80";
        String category = "gym";
        String priority = "HIGH";
        String description = "i want the viral lululemon bbl jacket";
        Double amount = 150.00;

        CreateGoalRequest request = CreateGoalRequest.builder()
                .withName(goalName)
                .withCategory(category)
                .withDescription(description)
                .withPriority(priority)
                .withGoalAmount(amount)
                .withUserId(userID)
                .build();

        CreateGoalResult result = createGoalActivity.handleRequest(request);

        verify(goalDao).saveGoal(any(Goal.class));

        assertNotNull(result.getGoalModel().getGoalId());
        assertEquals(goalName, result.getGoalModel().getName());
        assertEquals(userID, result.getGoalModel().getUserId());
        assertEquals(category, result.getGoalModel().getCategory());
        assertEquals(priority, result.getGoalModel().getPriority());
        assertEquals(description, result.getGoalModel().getDescription());
        assertEquals(amount, result.getGoalModel().getGoalAmount());
    }
}
