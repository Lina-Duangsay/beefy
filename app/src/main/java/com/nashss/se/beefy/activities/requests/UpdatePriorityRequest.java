package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdatePriorityRequest.Builder.class)
public class UpdatePriorityRequest {

    private final String userId;
    private final String goalId;
    private final String priority;

    public UpdatePriorityRequest(String userId, String goalId, String priority) {
        this.userId = userId;
        this.goalId = goalId;
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public String getGoalId() {
        return goalId;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "UpdatePriorityRequest{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
                ", priority='" + priority + '\'' +
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
        private String priority;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public Builder withPriority(String priority) {
            this.priority = priority;
            return this;
        }

        public UpdatePriorityRequest build() {
            return new UpdatePriorityRequest(userId, goalId, priority);
        }
    }
}
