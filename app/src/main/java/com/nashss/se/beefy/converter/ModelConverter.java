package com.nashss.se.beefy.converter;

import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    /**
     * Converts a provided {@link Goal} into a {@link GoalModel} representation.
     *
     * @param goal the goal to convert
     * @return the converted goal
     */
    public GoalModel toGoalModel(Goal goal) {
        return GoalModel.builder()
                .withUserId(goal.getUserId())
                .withName(goal.getName())
                .withCategory(goal.getCategory())
                .withDescription(goal.getDescription())
                .withGoalAmount(goal.getGoalAmount())
                .withPriority(goal.getPriority())
                .withGoalId(goal.getGoalId())
                .build();
    }


}
