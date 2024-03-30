package com.xeniac.oskardemoproject.feature_home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeniac.oskardemoproject.core.util.Event
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.core.util.UiEvent
import com.xeniac.oskardemoproject.feature_home.domain.use_cases.HomeUseCases
import com.xeniac.oskardemoproject.feature_home.util.HomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userToken = savedStateHandle.getStateFlow(
        key = "userToken",
        initialValue = ""
    )

    private val _logoutEventChannel = Channel<Event>()
    val logoutEventChannel = _logoutEventChannel.receiveAsFlow()

    init {
        getUserToken()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetUserToken -> getUserToken()
            HomeEvent.Logout -> logout()
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        savedStateHandle["userToken"] = homeUseCases.getUserTokenUseCase.get()()
    }

    private fun logout() = viewModelScope.launch {
        when (val logoutResult = homeUseCases.logoutUseCase.get()()) {
            is Resource.Success -> {
                _logoutEventChannel.send(HomeUiEvent.ClearCoilCache)
                _logoutEventChannel.send(HomeUiEvent.NavigateToAuthScreen)
            }
            is Resource.Error -> {
                logoutResult.message?.let { message ->
                    _logoutEventChannel.send(UiEvent.ShowSnackbar(message))
                }
            }
        }
    }
}