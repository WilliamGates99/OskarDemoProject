package com.xeniac.oskardemoproject.feature_auth.presentation.register

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
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val networkErrorState = savedStateHandle.getStateFlow(
        key = "networkErrorState",
        initialValue = NetworkErrorState()
    )

    private val registerFlowId = savedStateHandle.getStateFlow(
        key = "registerFlowId",
        initialValue = ""
    )

    val registerUiNodes = savedStateHandle.getStateFlow(
        key = "registerUiNodes",
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

    val isRegistrationFlowLoading = savedStateHandle.getStateFlow(
        key = "isRegistrationFlowLoading",
        initialValue = true
    )

    val isRegisterLoading = savedStateHandle.getStateFlow(
        key = "isRegisterLoading",
        initialValue = false
    )

    private val _registerEventChannel = Channel<Event>()
    val registerEventChannel = _registerEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        resetNetworkErrorState()

        when (event) {
            is RegisterEvent.TextFieldValueChanged -> {
                savedStateHandle["textFieldsMap"] = textFieldsMap.value.toMutableMap().apply {
                    replace(
                        /* key = */ event.identifier,
                        /* value = */ get(event.identifier)?.copy(
                            text = event.newValue
                        ) ?: CustomTextFieldState()
                    )
                }
            }
            RegisterEvent.GetRegistrationFlow -> getRegistrationFlow()
            RegisterEvent.Register -> register()
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
                    getRegistrationFlowResult.data?.let { getRegistrationFlowResponse ->
                        savedStateHandle["registerFlowId"] = getRegistrationFlowResponse.id
                        savedStateHandle["registerUiNodes"] = getRegistrationFlowResponse.ui.nodes

                        getRegistrationFlowResponse.ui.nodes.forEach { node ->
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

    private fun register() = viewModelScope.launch {
        if (NetworkObserverHelper.networkStatus == ConnectivityObserver.Status.AVAILABLE) {
            savedStateHandle["isRegisterLoading"] = true

            val submitRegistrationResult = authUseCases.submitRegistrationUseCase.get()(
                flowId = registerFlowId.value,
                textFieldsMap = textFieldsMap.value
            )

            when (submitRegistrationResult) {
                is Resource.Success -> {
                    savedStateHandle["isRegisterLoading"] = false
                    _registerEventChannel.send(AuthUiEvent.NavigateToHomeScreen)
                }
                is Resource.Error -> {
                    submitRegistrationResult.message?.let { message ->
                        _registerEventChannel.send(UiEvent.ShowSnackbar(message))
                    }
                    savedStateHandle["isRegisterLoading"] = false
                }
            }
        } else {
            _registerEventChannel.send(
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