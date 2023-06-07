package com.nashss.se.beefy.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "goals")
public class Goal {

    public static final String GOALS_BY_CATEGORY_INDEX = "GoalsByCategoryIndex";
    public static final String GOALS_BY_NAME_INDEX = "GoalsByNameIndex";
    private String userId;
    private String name;
    private String category;
    private String description;
    private Double goalAmount;
    private String priority;
    private String goalId;
    private Boolean completionStatus;

    @DynamoDBHashKey(attributeName = "goalId")
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = GOALS_BY_NAME_INDEX, attributeName = "goalName")
    @DynamoDBAttribute(attributeName = "goalName")
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = GOALS_BY_CATEGORY_INDEX, attributeName = "category")
    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "goalAmount")
    public Double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(Double goalAmount) {
        this.goalAmount = goalAmount;
    }

    @DynamoDBAttribute(attributeName = "priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @DynamoDBAttribute(attributeName = "completionStatus")
    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goals = (Goal) o;
        return Objects.equals(userId, goals.userId) && Objects.equals(name, goals.name) && Objects.equals(category, goals.category)
                && Objects.equals(description, goals.description) && Objects.equals(goalAmount, goals.goalAmount)
                && Objects.equals(priority, goals.priority) && Objects.equals(goalId, goals.goalId) && Objects.equals(completionStatus, goals.completionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, category, description, goalAmount, priority, goalId, completionStatus);
    }
}
