package com.xeniac.oskardemoproject.core.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xeniac.oskardemoproject.core.domain.use_cases.GetIsUserLoggedInUseCase
import com.xeniac.oskardemoproject.core.ui.navigation.Screen
import com.xeniac.oskardemoproject.core.ui.navigation.nav_graphs.NavGraphs
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsUserLoggedInUseCase: Lazy<GetIsUserLoggedInUseCase>,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _postSplashDestination = MutableStateFlow("")
    val postSplashDestination = _postSplashDestination.asStateFlow()

    private val _isSplashScreenLoading = MutableStateFlow(true)
    val isSplashScreenLoading = _isSplashScreenLoading.asStateFlow()

    init {
        getPostSplashDestination()
    }

    private fun getPostSplashDestination() = viewModelScope.launch {
        val isUserLoggedIn = getIsUserLoggedInUseCase.get()()

        if (isUserLoggedIn) {
            _postSplashDestination.value = Screen.HomeScreen.route
        } else {
            _postSplashDestination.value = NavGraphs.ROUTE_AUTH
        }

        _isSplashScreenLoading.value = false
    }
}