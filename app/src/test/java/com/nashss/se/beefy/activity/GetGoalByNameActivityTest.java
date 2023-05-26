package com.nashss.se.beefy.activity;


import com.nashss.se.beefy.activities.GetGoalByNameActivity;
import com.nashss.se.beefy.activities.requests.GetGoalByNameRequest;
import com.nashss.se.beefy.activities.results.GetGoalByNameResult;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetGoalByNameActivityTest {

    @Mock
    private GoalDao goalDao;

    private GetGoalByNameActivity getGoalByNameActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getGoalByNameActivity = new GetGoalByNameActivity(goalDao);
    }

    @Test
    public void handleRequest_savedGoal_returnsGoalModelInResult() {
        // GIVEN
        String expectedUserId = "kannabear";
        String expectedGoalName = "get new cat tree";
        String category = "furniture";
        String description = "i want a new, 8 foot cat tree";
        Double amount = 200.00;
        String priority = "HIGH";
        String goalId = "1234";

        Goal goal = new Goal();
        goal.setUserId(expectedUserId);
        goal.setName(expectedGoalName);
        goal.setCategory(category);
        goal.setDescription(description);
        goal.setGoalAmount(amount);
        goal.setPriority(priority);
        goal.setGoalId(goalId);

        when(goalDao.getGoal(expectedUserId, expectedGoalName)).thenReturn(goal);

        GetGoalByNameRequest request = GetGoalByNameRequest.builder()
                .withUserId(expectedUserId)
                .withName(expectedGoalName)
                .build();

        // WHEN
        GetGoalByNameResult result = getGoalByNameActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserId, result.getGoalModel().getUserId());
        assertEquals(expectedGoalName, result.getGoalModel().getName());
        assertEquals(category, result.getGoalModel().getCategory());
        assertEquals(description, result.getGoalModel().getDescription());
        assertEquals(amount, result.getGoalModel().getGoalAmount());
        assertEquals(priority, result.getGoalModel().getPriority());
        assertEquals(goalId, result.getGoalModel().getGoalId());

    }
}
