package com.xeniac.oskardemoproject.feature_home.di

import com.xeniac.oskardemoproject.feature_home.data.remote.HomeRepository
import com.xeniac.oskardemoproject.feature_home.domain.use_cases.GetUserTokenUseCase
import com.xeniac.oskardemoproject.feature_home.domain.use_cases.HomeUseCases
import com.xeniac.oskardemoproject.feature_home.domain.use_cases.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserTokenUseCase(
        homeRepository: HomeRepository
    ): GetUserTokenUseCase = GetUserTokenUseCase(homeRepository)

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(
        homeRepository: HomeRepository
    ): LogoutUseCase = LogoutUseCase(homeRepository)

    @Provides
    @ViewModelScoped
    fun provideHomeUseCases(
        getUserTokenUseCase: GetUserTokenUseCase,
        logoutUseCase: LogoutUseCase
    ): HomeUseCases = HomeUseCases(
        { getUserTokenUseCase },
        { logoutUseCase }
    )
}