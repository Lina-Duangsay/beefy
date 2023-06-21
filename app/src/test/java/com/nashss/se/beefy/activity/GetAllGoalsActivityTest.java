package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.GetAllGoalsActivity;
import com.nashss.se.beefy.activities.requests.GetAllGoalsRequest;
import com.nashss.se.beefy.activities.results.GetAllGoalsResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetAllGoalsActivityTest {

    @Mock
    private GoalDao goalDao;

    private GetAllGoalsActivity getAllGoalsActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        getAllGoalsActivity = new GetAllGoalsActivity(goalDao);
    }

    @Test
    void testHandleRequest() {
        String userId = "user123";
        GetAllGoalsRequest request = GetAllGoalsRequest.builder()
                .withUserId(userId)
                .build();

        Goal goal1 = new Goal();
        goal1.setGoalId("goal1");
        goal1.setName("Goal 1");
        Goal goal2 = new Goal();
        goal2.setGoalId("goal2");
        goal2.setName("Goal 2");
        List<Goal> listOfGoalsByUserId = Arrays.asList(goal1, goal2);

        when(goalDao.getGoalByUserId(userId)).thenReturn(listOfGoalsByUserId);

        GetAllGoalsResult result = getAllGoalsActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoalByUserId(userId);

        List<GoalModel> expectedGoalModelList = new ModelConverter().toGoalModelList(listOfGoalsByUserId);
        assertEquals(expectedGoalModelList, result.getGoalModel());
    }
}
