package com.codeex.task.di

import com.codeex.task.base.navigator.Navigator
import com.codeex.task.base.navigator.impl.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindAuthNavigator(impl: NavigatorImpl): Navigator
}