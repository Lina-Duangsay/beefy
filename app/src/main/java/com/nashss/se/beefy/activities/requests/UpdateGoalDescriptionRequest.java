package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateGoalDescriptionRequest.Builder.class)
public class UpdateGoalDescriptionRequest {

    private final String userId;
    private final String goalId;
    private final String description;

    public UpdateGoalDescriptionRequest(String userId, String goalId, String description) {
        this.userId = userId;
        this.goalId = goalId;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public String getGoalId() {
        return goalId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UpdateGoalDescriptionRequest{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    //CHECKSTYLE:OFF: Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String goalId;
        private String description;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public UpdateGoalDescriptionRequest build() {
            return new UpdateGoalDescriptionRequest(userId, goalId, description);
        }
    }
}
