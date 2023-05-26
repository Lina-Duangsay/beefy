package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class CreateGoalResult {
    private final GoalModel model;

    public CreateGoalResult(GoalModel model) {
        this.model = model;
    }

    public GoalModel getGoalModel(){
        return model;
    }
    @Override
    public String toString() {
        return "CreateGoalResult{" +
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

        public CreateGoalResult build() {
            return new CreateGoalResult(model);
        }

}
