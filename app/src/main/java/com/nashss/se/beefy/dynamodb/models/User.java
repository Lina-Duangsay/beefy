package com.nashss.se.beefy.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Users")
public class User {
    private String userId;
    private String username;
    private List<String> goals;
    private Integer goalCount;
    private Integer goalsCompleted;

    @DynamoDBIndexHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "goals")
    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    @DynamoDBAttribute(attributeName = "goalCount")
    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer goalCount) {
        this.goalCount = goalCount;
    }

    @DynamoDBAttribute(attributeName = "goalsCompleted")
    public Integer getGoalsCompleted() {
        return goalsCompleted;
    }

    public void setGoalsCompleted(Integer goalsCompleted) {
        this.goalsCompleted = goalsCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(goals, user.goals) && Objects.equals(goalCount, user.goalCount) && Objects.equals(goalsCompleted, user.goalsCompleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, goals, goalCount, goalsCompleted);
    }
}
