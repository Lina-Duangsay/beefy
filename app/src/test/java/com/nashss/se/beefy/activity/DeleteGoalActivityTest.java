package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.DeleteGoalActivity;
import com.nashss.se.beefy.activities.requests.DeleteGoalRequest;
import com.nashss.se.beefy.activities.results.DeleteGoalResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteGoalActivityTest {

    @Mock
    private GoalDao goalDao;

    private DeleteGoalActivity deleteGoalActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deleteGoalActivity = new DeleteGoalActivity(goalDao);
    }

    @Test
    void deleteGoalActivity_findActivity_activityDeleted() {
        String userId = "user123";
        String goalId = "goal123";
        DeleteGoalRequest request = DeleteGoalRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .build();

        Goal goal = new Goal();

        when(goalDao.getGoal(goalId)).thenReturn(goal);

        DeleteGoalResult result = deleteGoalActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoal(goalId);
        verify(goalDao, times(1)).deleteGoal(goal);

        GoalModel expectedGoalModel = new ModelConverter().toGoalModel(goal);
        assertEquals(expectedGoalModel, result.getGoalModel());
    }
}
