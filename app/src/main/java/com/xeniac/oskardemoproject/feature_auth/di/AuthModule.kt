package com.xeniac.oskardemoproject.feature_auth.di

import com.xeniac.oskardemoproject.feature_auth.data.remote.AuthRepository
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.AuthUseCases
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.GetLoginFlowUseCase
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.GetRegistrationFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideGetAboutUsUseCase(
        authRepository: AuthRepository
    ): GetLoginFlowUseCase = GetLoginFlowUseCase(authRepository)

    @Provides
    @ViewModelScoped
    fun provideGetRegistrationFlowUseCase(
        authRepository: AuthRepository
    ): GetRegistrationFlowUseCase = GetRegistrationFlowUseCase(authRepository)

    @Provides
    @ViewModelScoped
    fun provideAuthUseCases(
        getLoginFlowUseCase: GetLoginFlowUseCase,
        getRegistrationFlowUseCase: GetRegistrationFlowUseCase
    ): AuthUseCases = AuthUseCases(
        { getLoginFlowUseCase },
        { getRegistrationFlowUseCase }
    )
}