package com.nashss.se.beefy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.exceptions.GoalNotFoundException;
import com.nashss.se.beefy.exceptions.UserNotFoundException;
import com.nashss.se.beefy.metrics.MetricsConstants;
import com.nashss.se.beefy.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GoalDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;

    private GoalDao goalDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        goalDao = new GoalDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getGoal_withGoalId_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "beef wellington";
        String name = "cat treats";
        String goalId = "1234";
        when(dynamoDBMapper.load(Goal.class, goalId)).thenReturn(new Goal());

        // WHEN
        Goal goal = goalDao.getGoal(goalId);

        // THEN
        assertNotNull(goal);
        verify(dynamoDBMapper).load(Goal.class, goalId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETGOAL_GOALNOTFOUND_COUNT), anyDouble());

    }

    @Test
    public void GetGoal_GoalIdNotFound_throwsGoalNotFoundException() {
        // GIVEN
        String fakeId = null;
        when(dynamoDBMapper.load(Goal.class, fakeId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(GoalNotFoundException.class, () -> goalDao.getGoal(fakeId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETGOAL_GOALNOTFOUND_COUNT), anyDouble());
    }

}
