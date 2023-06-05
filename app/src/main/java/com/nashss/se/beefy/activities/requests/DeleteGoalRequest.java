package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = com.nashss.se.beefy.activities.requests.DeleteGoalRequest.Builder.class)
public class DeleteGoalRequest {

    private final String userId;
    private final String goalId;

    public DeleteGoalRequest(String userId, String goalId) {
        this.userId = userId;
        this.goalId = goalId;
    }

    public String getUserId() {
            return userId;
        }

    public String getGoalId() {
            return goalId;
        }

    @Override
    public String toString() {
        return "DeleteGoalRequest{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
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


        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public DeleteGoalRequest build(){
            return new DeleteGoalRequest(userId, goalId);
        }
    }
}

