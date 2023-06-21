package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetGoalByCategoryRequest.Builder.class)
public class GetGoalByCategoryRequest {
    
    private final String category;

    public GetGoalByCategoryRequest(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "GetGoalByNameRequest{" +
                "category='" + category + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String category;

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public GetGoalByCategoryRequest build() {
            return new GetGoalByCategoryRequest(category);
        }
    }
}
