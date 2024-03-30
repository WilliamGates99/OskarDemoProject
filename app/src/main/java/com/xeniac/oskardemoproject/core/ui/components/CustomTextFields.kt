package com.xeniac.oskardemoproject.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xeniac.oskardemoproject.R

@Composable
fun CustomOutlinedTextField(
    isLoading: Boolean,
    value: String,
    label: String?,
    modifier: Modifier = Modifier,
    isPasswordTextField: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    placeholder: String? = null,
    errorText: String? = null,
    supportingText: String? = null,
    leadingIcon: Painter? = null,
    leadingIconContentDescription: String? = null,
    leadingIconSize: Dp = 24.dp,
    trailingIcon: Painter? = null,
    trailingIconContentDescription: String? = null,
    trailingIconSize: Dp = 24.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    autoCorrect: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = keyboardCapitalization,
        autoCorrect = autoCorrect
    ),
    onValueChange: (newValue: String) -> Unit,
    keyboardAction: () -> Unit = {}
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        enabled = !isLoading,
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        label = if (label != null) {
            {
                Text(text = label)
            }
        } else null,
        placeholder = if (placeholder != null) {
            {
                Text(text = placeholder)
            }
        } else null,
        supportingText = when {
            supportingText != null -> {
                {
                    Text(text = supportingText)
                }
            }
            errorText != null -> {
                {
                    Text(text = errorText)
                }
            }
            else -> null
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(leadingIconSize)
                ) {
                    Image(
                        painter = leadingIcon,
                        contentDescription = leadingIconContentDescription,
                        modifier = Modifier.size(leadingIconSize)
                    )
                }
            }
        } else null,
        trailingIcon = when {
            isPasswordTextField -> {
                {
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(
                                id = R.drawable.ic_password_toggle_visible
                            ) else painterResource(id = R.drawable.ic_password_toggle_invisible),
                            contentDescription = if (isPasswordVisible) stringResource(
                                id = R.string.core_textfield_content_description_password_toggle_hide
                            ) else stringResource(id = R.string.core_textfield_content_description_password_toggle_show)
                        )
                    }
                }
            }
            trailingIcon != null -> {
                {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(trailingIconSize)
                    ) {
                        Image(
                            painter = trailingIcon,
                            contentDescription = trailingIconContentDescription,
                            modifier = Modifier.size(trailingIconSize)
                        )
                    }
                }
            }
            else -> null
        },
        visualTransformation = if (isPasswordTextField && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions {
            defaultKeyboardAction(imeAction)
            keyboardAction()
        }
    )
}