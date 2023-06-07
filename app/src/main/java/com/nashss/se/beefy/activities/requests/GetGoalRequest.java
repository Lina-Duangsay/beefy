package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetGoalRequest.Builder.class)
public class GetGoalRequest {
    private final String goalId;

    public GetGoalRequest(String goalId) {
        this.goalId = goalId;
    }

    public String getGoalId() {
        return goalId;
    }

    @Override
    public String toString() {
        return "GetGoalByNameRequest{" +
                "goalId='" + goalId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String goalId;

        public Builder withGoalId(String goalId) {
            this.goalId = goalId;
            return this;
        }

        public GetGoalRequest build() {
            return new GetGoalRequest(goalId);
        }
    }
}
