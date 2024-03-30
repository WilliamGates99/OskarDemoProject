package com.xeniac.oskardemoproject.feature_auth.util

import com.xeniac.oskardemoproject.core.util.Event

sealed class AuthUiEvent : Event() {
    data object NavigateToHomeScreen : AuthUiEvent()
}