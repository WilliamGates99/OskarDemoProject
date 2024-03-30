package com.xeniac.oskardemoproject.feature_auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginBtn(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "Already have an account?")

        TextButton(onClick = onLoginClick) {
            Text(text = "Login")
        }
    }
}