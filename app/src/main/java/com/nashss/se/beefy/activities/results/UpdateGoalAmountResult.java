package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class UpdateGoalAmountResult {

    private final GoalModel model;

    public UpdateGoalAmountResult(GoalModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "UpdateGoalAmountResult{" +
                "model=" + model +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GoalModel model;

        public Builder withGoalModel(GoalModel model){
            this.model = model;
            return this;
        }

        public UpdateGoalAmountResult build() {
            return new UpdateGoalAmountResult(model);
        }
    }
}
