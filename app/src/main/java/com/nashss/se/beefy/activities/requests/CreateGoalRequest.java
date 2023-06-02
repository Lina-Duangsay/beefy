package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateGoalRequest.Builder.class)
public class CreateGoalRequest {

    private final String userId;
    private final String name;
    private final String category;
    private final String description;
    private final Double goalAmount;
    private final String priority;

    public CreateGoalRequest(String userId, String name, String category, String description, Double goalAmount, String priority) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.goalAmount = goalAmount;
        this.priority = priority;
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

    @Override
    public String toString() {
        return "CreateGoalRequest{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", goalAmount=" + goalAmount +
                ", priority='" + priority + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF: Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String category;
        private String description;
        private Double goalAmount;
        private String priority;
        private String userId;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
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

        public CreateGoalRequest build() {
            return new CreateGoalRequest(userId, name, category, description, goalAmount, priority);
        }
    }

}
