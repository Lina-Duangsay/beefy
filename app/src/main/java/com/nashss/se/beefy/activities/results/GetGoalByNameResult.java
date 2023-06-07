package com.nashss.se.beefy.activities.results;

import com.nashss.se.beefy.models.GoalModel;

import java.util.ArrayList;
import java.util.List;

public class GetGoalByNameResult {
    private final List<GoalModel> goalModelList;

    public GetGoalByNameResult(List<GoalModel> goalModelList) {
        this.goalModelList = goalModelList;
    }

    public List<GoalModel> getGoalModel() {
        return new ArrayList<>(goalModelList);
    }

    @Override
    public String toString() {
        return "GetGoalByNameResult{" +
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
        public GetGoalByNameResult build(){
            return new GetGoalByNameResult(goalModelList);
        }
    }
}
