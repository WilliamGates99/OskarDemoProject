package com.xeniac.oskardemoproject.feature_auth.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeniac.oskardemoproject.core.data.local.ConnectivityObserver
import com.xeniac.oskardemoproject.core.domain.states.NetworkErrorState
import com.xeniac.oskardemoproject.core.util.NetworkObserverHelper
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.domain.models.Ui
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val networkErrorState = savedStateHandle.getStateFlow(
        key = "networkErrorState",
        initialValue = NetworkErrorState()
    )

    val loginFlowUi = savedStateHandle.getStateFlow<Ui?>(
        key = "loginFlowUi",
        initialValue = null
    )

    val isLoginFlowLoading = savedStateHandle.getStateFlow(
        key = "isLoginFlowLoading",
        initialValue = false
    )

    fun onEvent(event: LoginEvent) {
        resetNetworkErrorState()

        when (event) {
            LoginEvent.GetLoginFlow -> getLoginFlow()
        }
    }

    private fun resetNetworkErrorState() {
        savedStateHandle["networkErrorState"] = NetworkErrorState()
    }

    private fun getLoginFlow() = viewModelScope.launch {
        if (NetworkObserverHelper.networkStatus == ConnectivityObserver.Status.AVAILABLE) {
            savedStateHandle["isLoginFlowLoading"] = true
            when (val getLoginFlowResult = authUseCases.getLoginFlowUseCase.get()()) {
                is Resource.Success -> {
                    getLoginFlowResult.data?.let { loginUi ->
                        savedStateHandle["loginFlowUi"] = loginUi
                    }
                    savedStateHandle["isLoginFlowLoading"] = false
                }
                is Resource.Error -> {
                    getLoginFlowResult.message?.let { message ->
                        savedStateHandle["networkErrorState"] = networkErrorState.value.copy(
                            isNetworkErrorVisible = true,
                            networkErrorMessage = message
                        )
                    }
                    savedStateHandle["isLoginFlowLoading"] = false
                }
            }
        } else {
            savedStateHandle["networkErrorState"] = networkErrorState.value.copy(
                isOfflineErrorVisible = true
            )
        }
    }
}