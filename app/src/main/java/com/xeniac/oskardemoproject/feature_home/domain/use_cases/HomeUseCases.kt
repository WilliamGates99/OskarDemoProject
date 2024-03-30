package com.xeniac.oskardemoproject.feature_home.domain.use_cases

import dagger.Lazy

data class HomeUseCases(
    val getUserTokenUseCase: Lazy<GetUserTokenUseCase>,
    val logoutUseCase: Lazy<LogoutUseCase>
)