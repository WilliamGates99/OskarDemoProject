package com.xeniac.oskardemoproject.core.domain.states

import android.os.Parcelable
import com.xeniac.oskardemoproject.core.util.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkErrorState(
    val isOfflineErrorVisible: Boolean = false,
    val isNotFoundErrorVisible: Boolean = false,
    val isNetworkErrorVisible: Boolean = false,
    val networkErrorMessage: UiText? = null
) : Parcelable