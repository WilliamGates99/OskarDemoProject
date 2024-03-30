package com.xeniac.oskardemoproject.feature_home.presentation

sealed class HomeEvent {
    data object GetUserToken : HomeEvent()
    data object Logout : HomeEvent()
}