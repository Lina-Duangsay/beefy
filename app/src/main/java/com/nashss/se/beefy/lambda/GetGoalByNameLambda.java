package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.GetGoalByNameRequest;
import com.nashss.se.beefy.activities.results.GetGoalByNameResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetGoalByNameLambda extends LambdaActivityRunner<GetGoalByNameRequest, GetGoalByNameResult>
        implements RequestHandler<LambdaRequest<GetGoalByNameRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetGoalByNameRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetGoalByNameRequest.builder()
                                .withName(path.get("goalName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetGoalByNameGSIActivity().handleRequest(request)
        );
    }




}
