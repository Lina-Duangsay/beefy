package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class DeleteGoalResult {

    private final GoalModel model;

    public DeleteGoalResult(GoalModel model) {
        this.model = model;
    }

    public GoalModel getGoalModel() {
        return model;
    }

    @Override
    public String toString() {
        return "DeleteGoalResult{" +
                "model=" + model +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GoalModel model;

        public Builder withGoalModel(GoalModel model) {
            this.model = model;
            return this;
        }

        public DeleteGoalResult build() {
            return new DeleteGoalResult(model);
        }

    }

}
