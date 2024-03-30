package com.xeniac.oskardemoproject.feature_home.util

import com.xeniac.oskardemoproject.core.util.Event

sealed class HomeUiEvent : Event() {
    data object ClearCoilCache : HomeUiEvent()
    data object NavigateToAuthScreen : HomeUiEvent()
}