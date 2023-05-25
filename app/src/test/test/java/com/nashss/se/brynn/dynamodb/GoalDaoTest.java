package com.nashss.se.brynn.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.brynn.dynamodb.models.Goal;
import com.nashss.se.brynn.exceptions.UserNotFoundException;
import com.nashss.se.brynn.metrics.MetricsConstants;
import com.nashss.se.brynn.metrics.MetricsPublisher;
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
    public void getGoal_withUserIdAndGoalName_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "beef wellington";
        String name = "cat treats";
        when(dynamoDBMapper.load(Goal.class, userId, name)).thenReturn(new Goal());

        // WHEN
        Goal goal = goalDao.getGoal(userId, name);

        // THEN
        assertNotNull(goal);
        verify(dynamoDBMapper).load(Goal.class, userId, name);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETGOAL_GOALNOTFOUND_COUNT), anyDouble());

    }

    @Test
    public void GetGoal_UserIdNotFound_throwsUserNotFoundException() {
        // GIVEN
        String fakeId = null;
        String fakeName = null;
        when(dynamoDBMapper.load(Goal.class, fakeId, fakeName)).thenReturn(null);

        // WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> goalDao.getGoal(fakeId, fakeName));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETGOAL_USERNOTFOUND_COUNT), anyDouble());
    }

}
