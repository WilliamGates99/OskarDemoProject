package com.xeniac.oskardemoproject.feature_auth.presentation.register

sealed class RegisterEvent {
    data object GetRegistrationFlow : RegisterEvent()
}