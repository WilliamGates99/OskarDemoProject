package com.xeniac.oskardemoproject.feature_auth.domain.use_cases

import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.data.remote.AuthRepository
import com.xeniac.oskardemoproject.feature_auth.domain.models.Ui

class GetRegistrationFlowUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Resource<Ui> = authRepository.getRegistrationFlow()
}