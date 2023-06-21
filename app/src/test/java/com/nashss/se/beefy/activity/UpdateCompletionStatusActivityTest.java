import com.nashss.se.beefy.activities.UpdateCompletionStatusActivity;
import com.nashss.se.beefy.activities.requests.UpdateCompletionStatusRequest;
import com.nashss.se.beefy.activities.results.UpdateCompletionStatusResult;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.InvalidDescriptionException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCompletionStatusActivityTest {

    private GoalDao goalDao;
    private MetricsPublisher metricsPublisher;
    private UpdateCompletionStatusActivity updateCompletionStatusActivity;

    @BeforeEach
    void setUp() {
        goalDao = mock(GoalDao.class);
        metricsPublisher = mock(MetricsPublisher.class);
        updateCompletionStatusActivity = new UpdateCompletionStatusActivity(goalDao, metricsPublisher);
    }

    @Test
    void handleRequest_validRequest_updatesCompletionStatus() {
        String goalId = "1689";
        String userId = "selene";
        boolean completionStatus = true;

        UpdateCompletionStatusRequest request = UpdateCompletionStatusRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withCompletionStatus(completionStatus)
                .build();

        Goal initialGoal = new Goal();
        initialGoal.setUserId(userId);
        initialGoal.setCompletionStatus(!completionStatus);
        initialGoal.setGoalId(goalId);

        Goal updatedGoal = new Goal();
        updatedGoal.setUserId(userId);
        updatedGoal.setCompletionStatus(completionStatus);
        updatedGoal.setGoalId(goalId);

        when(goalDao.getGoal(goalId)).thenReturn(initialGoal);
        when(goalDao.saveGoal(updatedGoal)).thenReturn(updatedGoal);

        UpdateCompletionStatusResult result = updateCompletionStatusActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals(goalId, result.getModel().getGoalId());
        assertEquals(userId, result.getModel().getUserId());
        assertEquals(completionStatus, result.getModel().getCompletionStatus());
        verify(goalDao, times(1)).getGoal(goalId);
        verify(goalDao, times(1)).saveGoal(updatedGoal);
    }

    @Test
    void handleRequest_goalNotFound_throwsGoalNotFoundException() {
        String goalId = "1689";
        String userId = "selene";
        boolean completionStatus = true;

        UpdateCompletionStatusRequest request = UpdateCompletionStatusRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withCompletionStatus(completionStatus)
                .build();

        when(goalDao.getGoal(goalId)).thenReturn(null);

        assertThrows(GoalNotFoundException.class, () -> updateCompletionStatusActivity.handleRequest(request));

        verify(goalDao, times(1)).getGoal(goalId);
        verify(goalDao, never()).saveGoal(any());
    }

    @Test
    void handleRequest_incorrectGoalId_throwsGoalNotFoundException() {
        String goalId = "1689";
        String userId = "selene";
        boolean completionStatus = true;

        UpdateCompletionStatusRequest request = UpdateCompletionStatusRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withCompletionStatus(completionStatus)
                .build();

        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setCompletionStatus(!completionStatus);
        goal.setGoalId("differentGoalId");

        when(goalDao.getGoal(goalId)).thenReturn(goal);

        assertThrows(GoalNotFoundException.class, () -> updateCompletionStatusActivity.handleRequest(request));

        verify(goalDao, times(1)).getGoal(goalId);
        verify(goalDao, never()).saveGoal(any());
    }

    @Test
    void handleRequest_incorrectUserId_throwsUserNotFoundException() {
        String goalId = "1689";
        String userId = "selene";
        boolean completionStatus = true;

        UpdateCompletionStatusRequest request = UpdateCompletionStatusRequest.builder()
                .withUserId(userId)
                .withGoalId(goalId)
                .withCompletionStatus(completionStatus)
                .build();

        Goal goal = new Goal();
        goal.setUserId("differentUserId");
        goal.setCompletionStatus(!completionStatus);
        goal.setGoalId(goalId);

        when(goalDao.getGoal(goalId)).thenReturn(goal);

        assertThrows(UserNotFoundException.class, () -> updateCompletionStatusActivity.handleRequest(request));

        verify(goalDao, times(1)).getGoal(goalId);
        verify(goalDao, never()).saveGoal(any());
    }

}
