package com.xeniac.oskardemoproject.feature_auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.ui.components.LoadingIndicator
import com.xeniac.oskardemoproject.core.ui.components.NetworkErrorMessage
import com.xeniac.oskardemoproject.core.ui.components.OfflineErrorMessage
import com.xeniac.oskardemoproject.feature_auth.presentation.login.components.RegisterBtn

@Composable
fun RegisterScreen(
    onNavigateUp: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val networkErrorState by viewModel.networkErrorState.collectAsStateWithLifecycle()
    val registrationFlowUi by viewModel.registrationFlowUi.collectAsStateWithLifecycle()
    val isRegistrationFlowLoading by viewModel.isRegistrationFlowLoading.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (registrationFlowUi == null) {
            viewModel.onEvent(RegisterEvent.GetRegistrationFlow)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPaddings ->
        LoadingIndicator(
            isLoading = isRegistrationFlowLoading,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = !isRegistrationFlowLoading,
            modifier = Modifier.fillMaxSize()
        ) {
            if (networkErrorState.isOfflineErrorVisible) {
                OfflineErrorMessage(
                    onRetryClick = { viewModel.onEvent(RegisterEvent.GetRegistrationFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            } else if (networkErrorState.isNetworkErrorVisible) {
                NetworkErrorMessage(
                    errorMessage = networkErrorState.networkErrorMessage?.asString() ?: "",
                    onRetryClick = { viewModel.onEvent(RegisterEvent.GetRegistrationFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            } else if (registrationFlowUi != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = innerPaddings.calculateTopPadding(),
                            bottom = innerPaddings.calculateBottomPadding()
                        )
                        .windowInsetsPadding(WindowInsets.ime)
                        .verticalScroll(rememberScrollState())
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        )
                ) {
                    // TODO:
                    Text(
                        text = registrationFlowUi.toString(),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )

                    RegisterBtn(
                        onRegisterClick = onNavigateUp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                NetworkErrorMessage(
                    errorMessage = stringResource(id = R.string.error_something_went_wrong),
                    onRetryClick = { viewModel.onEvent(RegisterEvent.GetRegistrationFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}