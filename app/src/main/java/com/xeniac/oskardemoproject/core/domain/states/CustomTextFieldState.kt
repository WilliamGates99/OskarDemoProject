package com.xeniac.oskardemoproject.core.domain.states

import android.os.Parcelable
import com.xeniac.oskardemoproject.core.util.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomTextFieldState(
    val title: String? = null,
    var text: String = "",
    val isRequired: Boolean = false,
    val isPassword: Boolean = false,
    val isValid: Boolean = false,
    val isError: Boolean = false,
    val errorText: UiText? = null
) : Parcelable