package com.nashss.se.beefy.dependency;

import com.nashss.se.beefy.activities.*;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetGoalByNameActivity
     */
    GetGoalByNameActivity provideGetGoalByNameActivity();

}