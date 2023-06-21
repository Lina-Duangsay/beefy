package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.UpdatePriorityRequest;
import com.nashss.se.beefy.activities.results.UpdatePriorityResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePriorityActivityTest {

    @Mock
    private GoalDao goalDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdatePriorityActivity updatePriorityActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updatePriorityActivity = new UpdatePriorityActivity(goalDao, metricsPublisher);
    }

    @Test
    void handleRequest_ValidRequest_ReturnsUpdatePriorityResult() {
        UpdatePriorityRequest request = UpdatePriorityRequest.builder()
                .withUserId("user123")
                .withGoalId("goal123")
                .withPriority("high")
                .build();

        Goal goal = new Goal();
        goal.setGoalId("goal123");
        goal.setUserId("user123");

        when(goalDao.getGoal("goal123")).thenReturn(goal);
        when(goalDao.saveGoal(goal)).thenReturn(goal);

        UpdatePriorityResult result = updatePriorityActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals("goal123", result.getModel().getGoalId());
        assertEquals("high", result.getModel().getPriority());
        verify(goalDao).getGoal("goal123");
        verify(goalDao).saveGoal(goal);
        verifyNoMoreInteractions(goalDao);
        verifyNoInteractions(metricsPublisher);
    }

    @Test
    void handleRequest_GoalNotFound_ThrowsGoalNotFoundException() {
        UpdatePriorityRequest request = UpdatePriorityRequest.builder()
                .withUserId("user123")
                .withGoalId("goal123")
                .withPriority("high")
                .build();

        when(goalDao.getGoal("goal123")).thenReturn(null);

        assertThrows(GoalNotFoundException.class, () -> updatePriorityActivity.handleRequest(request));

        verify(goalDao).getGoal("goal123");
        verifyNoMoreInteractions(goalDao);
        verifyNoInteractions(metricsPublisher);
    }

    @Test
    void handleRequest_InvalidUserId_ThrowsUserNotFoundException() {
        UpdatePriorityRequest request = UpdatePriorityRequest.builder()
                .withUserId("user123")
                .withGoalId("goal123")
                .withPriority("high")
                .build();

        Goal goal = new Goal();
        goal.setGoalId("goal123");
        goal.setUserId("otherUser");

        when(goalDao.getGoal("goal123")).thenReturn(goal);

        assertThrows(UserNotFoundException.class, () -> updatePriorityActivity.handleRequest(request));

        verify(goalDao).getGoal("goal123");
    }
}
