package com.xeniac.oskardemoproject.core.domain.states

import android.os.Parcelable
import com.xeniac.oskardemoproject.core.util.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomTextFieldState(
    val text: String = "",
    val isValid: Boolean = false,
    val isError: Boolean = false,
    val errorText: UiText? = null
) : Parcelable