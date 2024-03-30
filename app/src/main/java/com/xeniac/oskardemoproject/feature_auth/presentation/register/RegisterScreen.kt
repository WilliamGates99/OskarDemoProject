package com.xeniac.oskardemoproject.feature_auth.presentation.register

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
import com.xeniac.oskardemoproject.feature_auth.presentation.register.components.LoginBtn

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    val registerUiNodes by viewModel.registerUiNodes.collectAsStateWithLifecycle()
    val textFieldsMap by viewModel.textFieldsMap.collectAsStateWithLifecycle()
    val submitButtonsTitle by viewModel.submitButtonsTitle.collectAsStateWithLifecycle()

    val isRegistrationFlowLoading by viewModel.isRegistrationFlowLoading.collectAsStateWithLifecycle()
    val isRegisterLoading by viewModel.isRegisterLoading.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (registerUiNodes.isEmpty()) {
            viewModel.onEvent(RegisterEvent.GetRegistrationFlow)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBarsIgnoringVisibility)
    ) { _ ->
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
            } else if (registerUiNodes.isNotEmpty()) {
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
                        text = "Register",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    textFieldsMap.forEach { (identifier, textFieldState) ->
                        CustomOutlinedTextField(
                            isLoading = isRegisterLoading,
                            value = textFieldState.text,
                            onValueChange = { newValue ->
                                viewModel.onEvent(
                                    RegisterEvent.TextFieldValueChanged(
                                        identifier = identifier,
                                        newValue = newValue
                                    )
                                )
                            },
                            label = textFieldState.title,
                            isPasswordTextField = textFieldState.isPassword,
                            supportingText = if (!textFieldState.isRequired) "Optional" else null,
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

                    LoginBtn(
                        onLoginClick = onNavigateUp,
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