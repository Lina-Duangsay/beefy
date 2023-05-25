package com.nashss.se.beefy.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Goals")
public class Goal {

    private String userId;
    private String name;
    private String category;
    private String description;
    private Double goalAmount;
    private String priority;
    private String goalId;

    @DynamoDBIndexHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBIndexRangeKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @DynamoDBAttribute(attributeName = "goalId")
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goals = (Goal) o;
        return Objects.equals(userId, goals.userId) && Objects.equals(name, goals.name) && Objects.equals(category, goals.category) && Objects.equals(description, goals.description) && Objects.equals(goalAmount, goals.goalAmount) && Objects.equals(priority, goals.priority) && Objects.equals(goalId, goals.goalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, category, description, goalAmount, priority, goalId);
    }
}
