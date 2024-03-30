package com.xeniac.oskardemoproject.feature_auth.domain.use_cases

import dagger.Lazy

data class AuthUseCases(
    val getLoginFlowUseCase: Lazy<GetLoginFlowUseCase>,
    val getRegistrationFlowUseCase: Lazy<GetRegistrationFlowUseCase>
)