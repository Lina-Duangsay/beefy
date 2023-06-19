package com.nashss.se.beefy.activities;

import com.nashss.se.beefy.activities.requests.GetGoalByCategoryRequest;
import com.nashss.se.beefy.activities.results.GetGoalByCategoryResult;
import com.nashss.se.beefy.converter.ModelConverter;
import com.nashss.se.beefy.dynamodb.GoalDao;
import com.nashss.se.beefy.dynamodb.models.Goal;
import com.nashss.se.beefy.models.GoalModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;


/**
 * Implementation of the GetGoalByCategoryActivity for the Beef's GetGoalByCategoryActivity API.
 *
 * This API allows the customer to get one of their saved goals by using a GSI to find the category.
 */
public class GetGoalByCategoryActivity {
    private final Logger log = LogManager.getLogger();
    private final GoalDao goalDao;


    /**
     * Instantiates a new GetGoalByCategoryActivity object.
     *
     * @param goalDao GoalDao to access the goal table.
     */
    @Inject
    public GetGoalByCategoryActivity(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    /**
     * This method handles the incoming request by retrieving the goal from the database using a GSI.
     * <p>
     * It then returns the goal.
     * <p>
     * If the playlist does not exist, this should throw a GoalNotFoundException.
     *
     * @param request request object containing the userId and goal name
     * @return getGoalByNameResult result object containing the API defined {@link com.nashss.se.beefy.models.GoalModel}
     */
    public GetGoalByCategoryResult handleRequest(final GetGoalByCategoryRequest request) {
        log.info("Received GetGoalByCategoryRequest {}", request);

        String requestedCategory = request.getCategory();

        List<Goal> listOfGoalsByCategory = goalDao.getGoalByCategory(requestedCategory);
        List<GoalModel> goalModelList = new ModelConverter().toGoalModelList(listOfGoalsByCategory);

        return GetGoalByCategoryResult.builder()
                .withGoalModelList(goalModelList)
                .build();
    }
}
