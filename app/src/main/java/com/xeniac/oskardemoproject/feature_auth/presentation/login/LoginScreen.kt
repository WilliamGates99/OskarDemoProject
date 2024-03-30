package com.xeniac.oskardemoproject.feature_auth.presentation.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.ui.components.CustomOutlinedTextField
import com.xeniac.oskardemoproject.core.ui.components.LoadingIndicator
import com.xeniac.oskardemoproject.core.ui.components.NetworkErrorMessage
import com.xeniac.oskardemoproject.core.ui.components.OfflineErrorMessage
import com.xeniac.oskardemoproject.feature_auth.presentation.login.components.RegisterBtn

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    val loginUiNodes by viewModel.loginUiNodes.collectAsStateWithLifecycle()
    val textFieldsMap by viewModel.textFieldsMap.collectAsStateWithLifecycle()
    val submitButtonsTitle by viewModel.submitButtonsTitle.collectAsStateWithLifecycle()

    val isLoginFlowLoading by viewModel.isLoginFlowLoading.collectAsStateWithLifecycle()
    val isLoginLoading by viewModel.isLoginLoading.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (loginUiNodes.isEmpty()) {
            viewModel.onEvent(LoginEvent.GetLoginFlow)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBarsIgnoringVisibility)
    ) { _ ->
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
            } else if (loginUiNodes.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.ime)
                        .verticalScroll(rememberScrollState())
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        )
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    textFieldsMap.forEach { (identifier, textFieldState) ->
                        CustomOutlinedTextField(
                            isLoading = isLoginLoading,
                            value = textFieldState.text,
                            onValueChange = { newValue ->
                                viewModel.onEvent(
                                    LoginEvent.TextFieldValueChanged(
                                        identifier = identifier,
                                        newValue = newValue
                                    )
                                )
                            },
                            label = textFieldState.title,
                            isPasswordTextField = textFieldState.isPassword,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp)) // 16 + 24 = 40.dp

                    Button(
                        onClick = {
                            // TODO:
                            Toast.makeText(
                                /* context = */ context,
                                /* text = */ "Clicked",
                                /* duration = */ Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = submitButtonsTitle)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.weight(1f))

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