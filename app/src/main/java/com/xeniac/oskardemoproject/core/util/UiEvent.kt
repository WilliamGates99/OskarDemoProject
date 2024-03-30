package com.xeniac.oskardemoproject.core.util

sealed class UiEvent : Event() {
    data class ShowToast(val message: UiText) : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
    data class ShowActionSnackbar(val message: UiText) : UiEvent()
}