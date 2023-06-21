package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.GetGoalByCategoryRequest;
import com.nashss.se.beefy.activities.results.GetGoalByCategoryResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetGoalByCategoryLambda extends LambdaActivityRunner<GetGoalByCategoryRequest, GetGoalByCategoryResult>
        implements RequestHandler<LambdaRequest<GetGoalByCategoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetGoalByCategoryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetGoalByCategoryRequest.builder()
                                .withCategory(path.get("category"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetGoalByCategoryActivity().handleRequest(request)
        );
    }

}
