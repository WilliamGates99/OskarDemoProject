package com.xeniac.oskardemoproject.core.domain.use_cases

import com.xeniac.oskardemoproject.core.data.local.PreferencesRepository

class GetIsUserLoggedInUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): Boolean = preferencesRepository.isUserLoggedIn()
}