package com.xeniac.oskardemoproject.feature_home.domain.use_cases

import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_home.data.remote.HomeRepository

class LogoutUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Resource<Nothing> = homeRepository.logout()
}