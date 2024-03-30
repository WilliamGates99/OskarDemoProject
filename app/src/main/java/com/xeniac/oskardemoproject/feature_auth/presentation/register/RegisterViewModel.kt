package com.xeniac.oskardemoproject.feature_auth.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeniac.oskardemoproject.core.data.local.ConnectivityObserver
import com.xeniac.oskardemoproject.core.domain.states.NetworkErrorState
import com.xeniac.oskardemoproject.core.util.NetworkObserverHelper
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.domain.models.Node
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val networkErrorState = savedStateHandle.getStateFlow(
        key = "networkErrorState",
        initialValue = NetworkErrorState()
    )

    val registerUiNodes = savedStateHandle.getStateFlow(
        key = "registerUiNodes",
        initialValue = emptyList<Node>()
    )

    val isRegistrationFlowLoading = savedStateHandle.getStateFlow(
        key = "isRegistrationFlowLoading",
        initialValue = true
    )

    fun onEvent(event: RegisterEvent) {
        resetNetworkErrorState()

        when (event) {
            RegisterEvent.GetRegistrationFlow -> getRegistrationFlow()
        }
    }

    private fun resetNetworkErrorState() {
        savedStateHandle["networkErrorState"] = NetworkErrorState()
    }

    private fun getRegistrationFlow() = viewModelScope.launch {
        if (NetworkObserverHelper.networkStatus == ConnectivityObserver.Status.AVAILABLE) {
            savedStateHandle["isRegistrationFlowLoading"] = true
            when (val getRegistrationFlowResult = authUseCases.getRegistrationFlowUseCase.get()()) {
                is Resource.Success -> {
                    getRegistrationFlowResult.data?.let { registerUiNodes ->
                        savedStateHandle["registerUiNodes"] = registerUiNodes
                    }
                    savedStateHandle["isRegistrationFlowLoading"] = false
                }
                is Resource.Error -> {
                    getRegistrationFlowResult.message?.let { message ->
                        savedStateHandle["networkErrorState"] = networkErrorState.value.copy(
                            isNetworkErrorVisible = true,
                            networkErrorMessage = message
                        )
                    }
                    savedStateHandle["isRegistrationFlowLoading"] = false
                }
            }
        } else {
            savedStateHandle["networkErrorState"] = networkErrorState.value.copy(
                isOfflineErrorVisible = true
            )
        }
    }
}