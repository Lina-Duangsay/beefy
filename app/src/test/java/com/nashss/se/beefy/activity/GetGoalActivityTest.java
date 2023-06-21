import com.nashss.se.beefy.activities.GetGoalActivity;
import com.nashss.se.beefy.activities.requests.GetGoalRequest;
import com.nashss.se.beefy.activities.results.GetGoalResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.models.GoalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetGoalActivityTest {

    private GoalDao goalDao;
    private GetGoalActivity getGoalActivity;

    @BeforeEach
    void setUp() {
        goalDao = mock(GoalDao.class);
        getGoalActivity = new GetGoalActivity(goalDao);
    }

    @Test
    void handleRequest_validRequest_returnsGoal() {
        // GIVEN
        String goalId = "1689";
        GetGoalRequest request = GetGoalRequest.builder()
                .withGoalId(goalId)
                .build();

        Goal goal = new Goal();
        goal.setGoalId(goalId);
        goal.setUserId("selene");
        goal.setCompletionStatus(false);

        GoalModel expectedGoalModel = new ModelConverter().toGoalModel(goal);

        when(goalDao.getGoal(goalId)).thenReturn(goal);

        // WHEN
        GetGoalResult result = getGoalActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        assertEquals(expectedGoalModel, result.getGoalModel());
        verify(goalDao, times(1)).getGoal(goalId);
    }

}
