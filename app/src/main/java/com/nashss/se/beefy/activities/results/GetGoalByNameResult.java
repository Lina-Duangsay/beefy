package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class GetGoalByNameResult {
    private final GoalModel goalModel;

    public GetGoalByNameResult(GoalModel goalModel) {
        this.goalModel = goalModel;
    }

    public GoalModel getGoalModel() {
        return goalModel;
    }

    @Override
    public String toString() {
        return "GetGoalByNameResult{" +
                "goalModel=" + goalModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GoalModel goalModel;

        public Builder withGoal(GoalModel goalModel) {
            this.goalModel = goalModel;
            return this;
        }

        public GetGoalByNameResult build() {
            return new GetGoalByNameResult(goalModel);
        }
    }
}
