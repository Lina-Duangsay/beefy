package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.UpdateGoalDescriptionRequest;
import com.nashss.se.beefy.activities.requests.UpdatePriorityRequest;
import com.nashss.se.beefy.activities.results.UpdateGoalDescriptionResult;
import com.nashss.se.beefy.activities.results.UpdatePriorityResult;

public class UpdatePriorityLambda extends LambdaActivityRunner<UpdatePriorityRequest, UpdatePriorityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdatePriorityRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdatePriorityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdatePriorityRequest unauthenticatedRequest = input.fromBody(UpdatePriorityRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdatePriorityRequest.builder()
                                    .withPriority(unauthenticatedRequest.getPriority())
                                    .withGoalId(unauthenticatedRequest.getGoalId())
                                    .withUserId(claims.get("email"))
                                    .build());
                    },
                (request, serviceComponent) -> serviceComponent.provideUpdatePriorityActivity().handleRequest(request)
        );
    }
}
