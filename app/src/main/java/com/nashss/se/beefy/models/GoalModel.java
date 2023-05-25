package com.nashss.se.beefy.models;

import java.util.Objects;

public class GoalModel {
    private final String userId;
    private final String name;
    private final String category;
    private final String description;
    private final Double goalAmount;
    private final String priority;
    private final String goalId;

    public GoalModel(String userId, String name, String category, String description,
                     Double goalAmount, String priority, String goalId) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.goalAmount = goalAmount;
        this.priority = priority;
        this.goalId = goalId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Double getGoalAmount() {
        return goalAmount;
    }

    public String getPriority() {
        return priority;
    }

    public String getGoalId() {
        return goalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalModel goalModel = (GoalModel) o;
        return Objects.equals(userId, goalModel.userId) && Objects.equals(name, goalModel.name)
                && Objects.equals(category, goalModel.category) && Objects.equals(description, goalModel.description)
                && Objects.equals(goalAmount, goalModel.goalAmount) && Objects.equals(priority, goalModel.priority)
                && Objects.equals(goalId, goalModel.goalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, category, description, goalAmount, priority, goalId);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String name;
        private String category;
        private String description;
        private Double goalAmount;
        private String priority;
        private String goalId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withGoalAmount(Double goalAmount) {
            this.goalAmount = goalAmount;
            return this;
        }

        public Builder withPriority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public GoalModel build() {
            return new GoalModel(userId, name, category, description, goalAmount, priority, goalId);
        }
    }

}
