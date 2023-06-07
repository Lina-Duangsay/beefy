package com.nashss.se.beefy.activities.results;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.beefy.activities.requests.UpdateGoalDescriptionRequest;
import com.nashss.se.beefy.models.GoalModel;

public class UpdatePriorityResult {

    private final GoalModel model;


    public UpdatePriorityResult(GoalModel model) {
        this.model = model;
    }

    public GoalModel getModel() {
        return model;
    }

    //CHECKSTYLE:OFF: Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private GoalModel model;

        public Builder withGoalModel(GoalModel model){
            this.model = model;
            return this;
        }

        public UpdatePriorityResult build() {
            return new UpdatePriorityResult(model);
        }
    }
}
