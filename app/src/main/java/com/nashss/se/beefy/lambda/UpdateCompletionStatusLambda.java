package com.nashss.se.beefy.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.beefy.activities.requests.UpdateCompletionStatusRequest;
import com.nashss.se.beefy.activities.results.UpdateCompletionStatusResult;

public class UpdateCompletionStatusLambda extends LambdaActivityRunner<UpdateCompletionStatusRequest, UpdateCompletionStatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateCompletionStatusRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateCompletionStatusRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateCompletionStatusRequest unauthenticatedRequest = input.fromBody(UpdateCompletionStatusRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateCompletionStatusRequest.builder()
                                    .withCompletionStatus(unauthenticatedRequest.getCompletionStatus())
                                    .withGoalId(unauthenticatedRequest.getGoalId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateCompletionStatusActivity().handleRequest(request)
        );
    }





}
