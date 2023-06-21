package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetGoalByNameRequest.Builder.class)
public class GetGoalByNameRequest {
    
    private final String name;

    public GetGoalByNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GetGoalByNameRequest{" +
                "goalName='" + name + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public GetGoalByNameRequest build() {
            return new GetGoalByNameRequest(name);
        }
    }
}
