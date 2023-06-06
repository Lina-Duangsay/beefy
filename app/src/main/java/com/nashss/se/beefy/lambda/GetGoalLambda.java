package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.GetGoalRequest;
import com.nashss.se.beefy.activities.results.GetGoalResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetGoalLambda extends LambdaActivityRunner<GetGoalRequest, GetGoalResult>
        implements RequestHandler<LambdaRequest<GetGoalRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetGoalRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetGoalRequest.builder()
                                .withGoalId(path.get("goalId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetGoalByNameActivity().handleRequest(request)
        );
    }
}
