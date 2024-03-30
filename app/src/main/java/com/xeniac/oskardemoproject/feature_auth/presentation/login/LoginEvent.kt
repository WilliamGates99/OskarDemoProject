package com.xeniac.oskardemoproject.feature_auth.presentation.login

sealed class LoginEvent {
    data class TextFieldValueChanged(
        val identifier: String,
        val newValue: String
    ) : LoginEvent()

    data object GetLoginFlow : LoginEvent()
}