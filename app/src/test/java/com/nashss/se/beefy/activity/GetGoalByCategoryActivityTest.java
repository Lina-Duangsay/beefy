package com.nashss.se.beefy.activity;

import com.nashss.se.beefy.activities.GetGoalByCategoryActivity;
import com.nashss.se.beefy.activities.GetGoalByNameActivity;
import com.nashss.se.beefy.activities.requests.GetGoalByCategoryRequest;
import com.nashss.se.beefy.activities.requests.GetGoalByNameRequest;
import com.nashss.se.beefy.activities.results.GetGoalByCategoryResult;
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

class GetGoalByCategoryActivityTest {

    @Mock
    private GoalDao goalDao;

    private GetGoalByCategoryActivity getGoalByCategoryActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        getGoalByCategoryActivity = new GetGoalByCategoryActivity(goalDao);
    }

    @Test
    void getGoalByCategoryActivity_goodRequest_returnsModelList() {
        String requestedCategory = "Example Goal";
        GetGoalByCategoryRequest request = GetGoalByCategoryRequest.builder()
                .withCategory(requestedCategory)
                .build();

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal());

        when(goalDao.getGoalByCategory(requestedCategory)).thenReturn(goals);

        GetGoalByCategoryResult result = getGoalByCategoryActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoalByCategory(requestedCategory);
        List<GoalModel> expectedGoalModelList = new ModelConverter().toGoalModelList(goals);
        assertEquals(expectedGoalModelList, result.getGoalModel());
    }

    @Test
    void getGoalByCategoryActivity_badRequest_returnsEmptyModelList() {
        String requestedCategory = "Non-existent Goal";
        GetGoalByCategoryRequest request = GetGoalByCategoryRequest.builder()
                .withCategory(requestedCategory)
                .build();

        when(goalDao.getGoalByCategory(requestedCategory)).thenReturn(new ArrayList<>());

        GetGoalByCategoryResult result = getGoalByCategoryActivity.handleRequest(request);

        verify(goalDao, times(1)).getGoalByCategory(requestedCategory);

        assertTrue(result.getGoalModel().isEmpty());
    }
}

