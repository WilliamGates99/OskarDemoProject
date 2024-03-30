package com.xeniac.oskardemoproject.core.di

import com.xeniac.oskardemoproject.feature_auth.data.remote.AuthRepository
import com.xeniac.oskardemoproject.feature_auth.data.remote.AuthRepositoryImpl
import com.xeniac.oskardemoproject.feature_home.data.remote.HomeRepository
import com.xeniac.oskardemoproject.feature_home.data.remote.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @ViewModelScoped
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository
}