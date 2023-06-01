package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class UpdateGoalAmountRequest {
    private String userId;
    private String goalId;
    private Double amount;

    public UpdateGoalAmountRequest(String userId, String goalId, Double amount) {
        this.userId = userId;
        this.goalId = goalId;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getGoalId() {
        return goalId;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "UpdateGoalAmountRequest{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
                ", amount=" + amount +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String goalId;
        private Double amount;


}
