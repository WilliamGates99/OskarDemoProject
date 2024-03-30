package com.xeniac.oskardemoproject.feature_auth.presentation.register

sealed class RegisterEvent {
    data class TextFieldValueChanged(
        val identifier: String,
        val newValue: String
    ) : RegisterEvent()

    data object GetRegistrationFlow : RegisterEvent()
    data object Register : RegisterEvent()
}