package com.xeniac.oskardemoproject.feature_auth.domain.use_cases

import com.xeniac.oskardemoproject.core.domain.states.CustomTextFieldState
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.data.remote.AuthRepository

class SubmitRegistrationUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        flowId: String,
        textFieldsMap: Map<String, CustomTextFieldState>
    ): Resource<Nothing> = authRepository.submitRegistration(flowId, textFieldsMap)
}