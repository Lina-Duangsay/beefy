package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.GetGoalByNameActivity;
import com.nashss.se.beefy.activities.requests.GetGoalByNameRequest;
import com.nashss.se.beefy.activities.results.GetGoalByNameResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetGoalByNameActivityTest {

    @Mock
    private GoalDao goalDao;

    private GetGoalByNameActivity getGoalByNameActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        getGoalByNameActivity = new GetGoalByNameActivity(goalDao);
    }

    @Test
    void getGoalByNameActivity_goodRequest_returnsModelList() {
        String requestedName = "Example Goal";
        GetGoalByNameRequest request = GetGoalByNameRequest.builder()
                .withName(requestedName)
                .build();

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal());

        when(goalDao.getGoalByName(requestedName)).thenReturn(goals);

        GetGoalByNameResult result = getGoalByNameActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoalByName(requestedName);
        List<GoalModel> expectedGoalModelList = new ModelConverter().toGoalModelList(goals);
        assertEquals(expectedGoalModelList, result.getGoalModel());
    }

    @Test
    void getGoalByNameActivity_badRequest_returnsEmptyModelList() {
        String requestedName = "Non-existent Goal";
        GetGoalByNameRequest request = GetGoalByNameRequest.builder()
                .withName(requestedName)
                .build();

        when(goalDao.getGoalByName(requestedName)).thenReturn(new ArrayList<>());

        GetGoalByNameResult result = getGoalByNameActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoalByName(requestedName);

        assertTrue(result.getGoalModel().isEmpty());
    }
}

