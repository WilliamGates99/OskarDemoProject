package com.xeniac.oskardemoproject.core.di

import com.xeniac.oskardemoproject.core.data.local.PreferencesRepository
import com.xeniac.oskardemoproject.core.domain.use_cases.GetIsUserLoggedInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object CoreModule {

    @Provides
    @ViewModelScoped
    fun provideGetIsUserLoggedInUseCase(
        preferencesRepository: PreferencesRepository
    ): GetIsUserLoggedInUseCase = GetIsUserLoggedInUseCase(preferencesRepository)
}