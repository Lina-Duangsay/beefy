package com.nashss.se.beefy.activities.requests;

public class GetGoalByNameRequest {
    private final String userId;
    private final String name;


    public GetGoalByNameRequest(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GetGoalByNameRequest{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String name;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public GetGoalByNameRequest build() {
            return new GetGoalByNameRequest(userId, name);
        }
    }
}
