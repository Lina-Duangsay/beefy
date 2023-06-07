package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class UpdateCompletionStatusResult {

    private final GoalModel model;

    public UpdateCompletionStatusResult(GoalModel model) {
        this.model = model;
    }

    public GoalModel getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "UpdateCompletionStatusResult{" +
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

        public UpdateCompletionStatusResult build() {
            return new UpdateCompletionStatusResult(model);
        }
    }


}
