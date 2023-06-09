package com.nashss.se.beefy.utility;

import org.apache.commons.lang3.RandomStringUtils;

public final class ServiceUtils {

    static final int USER_ID_LENGTH = 5;
    static final int GOAL_ID_LENGTH = 5;

    private ServiceUtils() {
    }

    public static String generateGoalId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
