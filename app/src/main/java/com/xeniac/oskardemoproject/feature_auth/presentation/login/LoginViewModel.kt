package com.xeniac.oskardemoproject.feature_auth.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.data.local.ConnectivityObserver
import com.xeniac.oskardemoproject.core.domain.states.CustomTextFieldState
import com.xeniac.oskardemoproject.core.domain.states.NetworkErrorState
import com.xeniac.oskardemoproject.core.util.Event
import com.xeniac.oskardemoproject.core.util.NetworkObserverHelper
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.core.util.UiEvent
import com.xeniac.oskardemoproject.core.util.UiText
import com.xeniac.oskardemoproject.feature_auth.domain.models.Node
import com.xeniac.oskardemoproject.feature_auth.domain.use_cases.AuthUseCases
import com.xeniac.oskardemoproject.feature_auth.util.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val loginFlowId = savedStateHandle.getStateFlow(
        key = "loginFlowId",
        initialValue = ""
    )

    val loginUiNodes = savedStateHandle.getStateFlow(
        key = "loginUiNodes",
        initialValue = emptyList<Node>()
    )

    val textFieldsMap = savedStateHandle.getStateFlow(
        key = "textFieldsMap",
        initialValue = emptyMap<String, CustomTextFieldState>()
    )

    val submitButtonsTitle = savedStateHandle.getStateFlow(
        key = "submitButtonsTitle",
        initialValue = ""
    )

    val isLoginFlowLoading = savedStateHandle.getStateFlow(
        key = "isLoginFlowLoading",
        initialValue = true
    )

    val isLoginLoading = savedStateHandle.getStateFlow(
        key = "isLoginLoading",
        initialValue = false
    )

    private val _loginEventChannel = Channel<Event>()
    val loginEventChannel = _loginEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        resetNetworkErrorState()

        when (event) {
            is LoginEvent.TextFieldValueChanged -> {
                savedStateHandle["textFieldsMap"] = textFieldsMap.value.toMutableMap().apply {
                    replace(
                        /* key = */ event.identifier,
                        /* value = */ get(event.identifier)?.copy(
                            text = event.newValue
                        ) ?: CustomTextFieldState()
                    )
                }
            }
            LoginEvent.GetLoginFlow -> getLoginFlow()
            LoginEvent.Login -> login()
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
                    getLoginFlowResult.data?.let { getLoginFlowResponse ->
                        savedStateHandle["loginFlowId"] = getLoginFlowResponse.id
                        savedStateHandle["loginUiNodes"] = getLoginFlowResponse.ui.nodes

                        getLoginFlowResponse.ui.nodes.forEach { node ->
                            if (node.meta?.label != null) {
                                if (node.attributes.type == "submit") {
                                    savedStateHandle["submitButtonsTitle"] = node.meta.label.text
                                } else {
                                    addTextField(
                                        identifier = node.attributes.name,
                                        textFieldState = CustomTextFieldState(
                                            title = node.meta.label.text,
                                            isRequired = node.attributes.required == true,
                                            isPassword = node.attributes.type == "password"
                                        )
                                    )
                                }
                            }
                        }
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

    private fun login() = viewModelScope.launch {
        if (NetworkObserverHelper.networkStatus == ConnectivityObserver.Status.AVAILABLE) {
            savedStateHandle["isLoginLoading"] = true

            val submitLoginResult = authUseCases.submitLoginUseCase.get()(
                flowId = loginFlowId.value,
                textFieldsMap = textFieldsMap.value
            )

            when (submitLoginResult) {
                is Resource.Success -> {
                    savedStateHandle["isLoginLoading"] = false
                    _loginEventChannel.send(AuthUiEvent.NavigateToHomeScreen)
                }
                is Resource.Error -> {
                    submitLoginResult.message?.let { message ->
                        _loginEventChannel.send(UiEvent.ShowSnackbar(message))
                    }
                    savedStateHandle["isLoginLoading"] = false
                }
            }
        } else {
            _loginEventChannel.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.error_network_connection_unavailable)
                )
            )
        }
    }

    private fun addTextField(
        identifier: String,
        textFieldState: CustomTextFieldState
    ) = viewModelScope.launch {
        savedStateHandle["textFieldsMap"] = textFieldsMap.value.toMutableMap().apply {
            put(
                key = identifier,
                value = textFieldState
            )
        }
    }
}