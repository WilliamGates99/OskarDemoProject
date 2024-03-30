package com.xeniac.oskardemoproject.feature_auth.presentation.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterBtn(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "Don't have an account?")

        TextButton(onClick = onRegisterClick) {
            Text(text = "Register")
        }
    }
}