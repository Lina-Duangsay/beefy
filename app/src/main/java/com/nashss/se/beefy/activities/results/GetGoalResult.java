package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

public class GetGoalResult {
    private final GoalModel goalModel;

    public GetGoalResult(GoalModel goalModel) {
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

        public GetGoalResult build() {
            return new GetGoalResult(goalModel);
        }
    }
}
