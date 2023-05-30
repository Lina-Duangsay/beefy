package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.nashss.se.beefy.activities.requests.CreateGoalRequest;
import com.nashss.se.beefy.activities.results.CreateGoalResult;

public class CreateGoalLambda extends LambdaActivityRunner<CreateGoalRequest, CreateGoalResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateGoalRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateGoalRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateGoalRequest unauthenticatedRequest = input.fromBody(CreateGoalRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateGoalRequest.builder()
                                    .withUserId(claims.get("userId"))
                                    .withName(claims.get("name"))
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withPriority(unauthenticatedRequest.getPriority())
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withGoalAmount(unauthenticatedRequest.getGoalAmount())
                                    .withName(unauthenticatedRequest.getName())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateGoalActivity().handleRequest(request)
        );
    }
}