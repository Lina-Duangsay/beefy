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
    GetGoalActivity provideGetGoalByNameActivity();

    /**
     * Provides the relevant activity.
     * @return CreateGoalActivity
     */
    CreateGoalActivity provideCreateGoalActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateGoalAmountActivity
     */
    UpdateGoalAmountActivity provideUpdateGoalAmountActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateGoalDescriptionActivity
     */
    UpdateGoalDescriptionActivity provideUpdateGoalDescriptionActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteGoalActivity
     */
    DeleteGoalActivity provideDeleteGoalActivity();

    GetGoalByCategoryActivity provideGetGoalByCategoryActivity();
}