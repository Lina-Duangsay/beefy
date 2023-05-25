package com.nashss.se.beefy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.beefy.dynamodb.models.User;
import com.nashss.se.beefy.exceptions.UserNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDao {

    private final DynamoDBMapper mapper;


    @Inject
    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a User by userId.
     *
     * If not found, throws UserNotFoundException.
     *
     * @param userId The userId to look up
     * @return The corresponding User if found
     */
    public User getUser(String userId) {
        User requestedUser = mapper.load(User.class, userId);
        if (null == requestedUser) {
            throw new UserNotFoundException(
                    String.format("Could not find User with ID '%s'", userId));
        }

        return requestedUser;
    }


}
