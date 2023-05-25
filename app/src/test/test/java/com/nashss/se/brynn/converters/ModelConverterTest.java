package com.nashss.se.brynn.converters;

import com.nashss.se.brynn.converter.ModelConverter;
import com.nashss.se.brynn.dynamodb.models.Goal;
import com.nashss.se.brynn.models.GoalModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toGoalModel_convertsGoal() {
        String expectedUserId = "kannabear";
        String expectedGoalName = "get new cat tree";
        String category = "furniture";
        String description = "i want a new, 8 foot cat tree";
        Double amount = 200.00;
        String priority = "HIGH";
        String goalId = "1234";

        Goal goal = new Goal();
        goal.setUserId(expectedUserId);
        goal.setName(expectedGoalName);
        goal.setCategory(category);
        goal.setDescription(description);
        goal.setGoalAmount(amount);
        goal.setPriority(priority);
        goal.setGoalId(goalId);

        GoalModel goalModel = modelConverter.toGoalModel(goal);
        assertEquals(expectedUserId, goalModel.getUserId());
        assertEquals(expectedGoalName, goalModel.getName());
        assertEquals(category, goalModel.getCategory());
        assertEquals(description, goalModel.getDescription());
        assertEquals(amount, goalModel.getGoalAmount());
        assertEquals(priority, goalModel.getPriority());
        assertEquals(goalId, goalModel.getGoalId());
    }
}
