package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.UpdateGoalAmountRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalAmountResult;

public class UpdateGoalAmountLambda extends LambdaActivityRunner<UpdateGoalAmountRequest, UpdateGoalAmountResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateGoalAmountRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateGoalAmountRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateGoalAmountRequest unauthenticatedRequest = input.fromBody(UpdateGoalAmountRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateGoalAmountRequest.builder()
                                    .withAmount(unauthenticatedRequest.getAmount())
                                    .withGoalId(unauthenticatedRequest.getGoalId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateGoalAmountActivity().handleRequest(request)
        );
    }

}
