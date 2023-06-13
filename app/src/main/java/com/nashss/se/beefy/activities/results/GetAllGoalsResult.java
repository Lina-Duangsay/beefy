package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllGoalsResult {
    private final List<GoalModel> goalModelList;

    public GetAllGoalsResult(List<GoalModel> goalModelList) {
        this.goalModelList = goalModelList;
    }

    public List<GoalModel> getGoalModel() {
        return new ArrayList<>(goalModelList);
    }

    @Override
    public String toString() {
        return "GetALlGoalsResult{" +
                "goalModelList=" + goalModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<GoalModel> goalModelList;

        public Builder withGoalModelList(List<GoalModel> goalModelList){
            this.goalModelList = new ArrayList<>(goalModelList);
            return this;
        }

        public GetAllGoalsResult build() {
            return new GetAllGoalsResult(goalModelList);
        }
    }
}
