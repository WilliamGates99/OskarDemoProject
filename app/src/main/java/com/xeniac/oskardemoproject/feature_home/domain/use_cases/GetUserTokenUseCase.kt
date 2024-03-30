package com.xeniac.oskardemoproject.feature_home.domain.use_cases

import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_home.data.remote.HomeRepository
import com.xeniac.oskardemoproject.feature_home.data.remote.UserToken

class GetUserTokenUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Resource<UserToken> = homeRepository.getUserToken()
}