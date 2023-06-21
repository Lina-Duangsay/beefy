package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.beefy.activities.requests.GetAllGoalsRequest;
import com.nashss.se.beefy.activities.results.GetAllGoalsResult;


public class GetAllGoalsLambda extends LambdaActivityRunner<GetAllGoalsRequest, GetAllGoalsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllGoalsRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllGoalsRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllGoalsRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllGoalsActivity().handleRequest(request)
        );
    }
}
