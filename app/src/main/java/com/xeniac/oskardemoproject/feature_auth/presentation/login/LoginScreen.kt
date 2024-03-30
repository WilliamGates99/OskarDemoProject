package com.xeniac.oskardemoproject.feature_auth.presentation.login

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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val networkErrorState by viewModel.networkErrorState.collectAsStateWithLifecycle()
    val loginFlowUi by viewModel.loginFlowUi.collectAsStateWithLifecycle()
    val isLoginFlowLoading by viewModel.isLoginFlowLoading.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (loginFlowUi == null) {
            viewModel.onEvent(LoginEvent.GetLoginFlow)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPaddings ->
        LoadingIndicator(
            isLoading = isLoginFlowLoading,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = !isLoginFlowLoading,
            modifier = Modifier.fillMaxSize()
        ) {
            if (networkErrorState.isOfflineErrorVisible) {
                OfflineErrorMessage(
                    onRetryClick = { viewModel.onEvent(LoginEvent.GetLoginFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            } else if (networkErrorState.isNetworkErrorVisible) {
                NetworkErrorMessage(
                    errorMessage = networkErrorState.networkErrorMessage?.asString() ?: "",
                    onRetryClick = { viewModel.onEvent(LoginEvent.GetLoginFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            } else if (loginFlowUi != null) {
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
                        text = loginFlowUi.toString(),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )

                    RegisterBtn(
                        onRegisterClick = onNavigateToRegister,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                NetworkErrorMessage(
                    errorMessage = stringResource(id = R.string.error_something_went_wrong),
                    onRetryClick = { viewModel.onEvent(LoginEvent.GetLoginFlow) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}