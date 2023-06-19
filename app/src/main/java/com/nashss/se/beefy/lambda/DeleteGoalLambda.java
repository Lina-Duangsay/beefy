package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.beefy.activities.requests.DeleteGoalRequest;
import com.nashss.se.beefy.activities.results.DeleteGoalResult;

public class DeleteGoalLambda extends LambdaActivityRunner<DeleteGoalRequest, DeleteGoalResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteGoalRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteGoalRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteGoalRequest unauthenticatedRequest = input.fromBody(DeleteGoalRequest.class);
                    return input.fromUserClaims(claims ->
                            DeleteGoalRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withGoalId(unauthenticatedRequest.getGoalId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteGoalActivity().handleRequest(request)
        );
    }




}
