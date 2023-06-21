package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.beefy.activities.requests.UpdateGoalDescriptionRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalDescriptionResult;

public class UpdateGoalDescriptionLambda extends LambdaActivityRunner<UpdateGoalDescriptionRequest, UpdateGoalDescriptionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateGoalDescriptionRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateGoalDescriptionRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateGoalDescriptionRequest unauthenticatedRequest = input.fromBody(UpdateGoalDescriptionRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateGoalDescriptionRequest.builder()
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withGoalId(unauthenticatedRequest.getGoalId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateGoalDescriptionActivity().handleRequest(request)
        );
    }
}
