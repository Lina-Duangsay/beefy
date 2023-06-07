package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateCompletionStatusRequest.Builder.class)
public class UpdateCompletionStatusRequest {
    private final String userId;
    private final String goalId;
    private final Boolean completionStatus;

    public UpdateCompletionStatusRequest(String userId, String goalId, Boolean completionStatus) {
        this.userId = userId;
        this.goalId = goalId;
        this.completionStatus = completionStatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getGoalId() {
        return goalId;
    }

    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    @Override
    public String toString() {
        return "UpdateCompletionStatusRequest{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
                ", completionStatus=" + completionStatus +
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
        private Boolean completionStatus;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public Builder withCompletionStatus(Boolean completionStatus) {
            this.completionStatus = completionStatus;
            return this;
        }

        public UpdateCompletionStatusRequest build() {
            return new UpdateCompletionStatusRequest(userId, goalId, completionStatus);
        }
    }
}
