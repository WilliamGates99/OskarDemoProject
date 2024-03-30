package com.xeniac.oskardemoproject.core.di

import com.xeniac.oskardemoproject.core.data.local.PreferencesRepository
import com.xeniac.oskardemoproject.core.data.local.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositorySingletonModule {

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}