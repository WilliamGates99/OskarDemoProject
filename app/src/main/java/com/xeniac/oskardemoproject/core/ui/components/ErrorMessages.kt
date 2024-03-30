package com.xeniac.oskardemoproject.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xeniac.oskardemoproject.R

@Composable
fun OfflineErrorMessage(
    modifier: Modifier = Modifier,
    errorMessage: String = stringResource(id = R.string.error_network_connection_unavailable),
    errorMessageFontSize: TextUnit = 14.sp,
    errorMessageColor: Color = MaterialTheme.colorScheme.error,
    errorMessageTextAlign: TextAlign = TextAlign.Center,
    retryBtn: String = stringResource(id = R.string.error_btn_retry),
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier.padding(all = 40.dp)
    ) {
        Text(
            text = errorMessage,
            fontSize = errorMessageFontSize,
            color = errorMessageColor,
            textAlign = errorMessageTextAlign
        )

        Button(onClick = onRetryClick) {
            Text(text = retryBtn)
        }
    }
}

@Composable
fun NetworkErrorMessage(
    errorMessage: String,
    modifier: Modifier = Modifier,
    errorMessageFontSize: TextUnit = 14.sp,
    errorMessageColor: Color = MaterialTheme.colorScheme.error,
    errorMessageTextAlign: TextAlign = TextAlign.Center,
    retryBtn: String = stringResource(id = R.string.error_btn_retry),
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier.padding(all = 40.dp)
    ) {
        Text(
            text = errorMessage,
            fontSize = errorMessageFontSize,
            color = errorMessageColor,
            textAlign = errorMessageTextAlign
        )

        Button(onClick = onRetryClick) {
            Text(text = retryBtn)
        }
    }
}