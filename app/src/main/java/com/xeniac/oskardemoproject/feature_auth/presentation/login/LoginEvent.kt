package com.xeniac.oskardemoproject.feature_auth.presentation.login

sealed class LoginEvent {
    data object GetLoginFlow : LoginEvent()
}