package com.xeniac.oskardemoproject.core.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.xeniac.oskardemoproject.core.ui.navigation.nav_graphs.SetupRootNavGraph
import com.xeniac.oskardemoproject.core.ui.theme.OskarDemoProjectTheme
import com.xeniac.oskardemoproject.core.util.NetworkObserverHelper.observeNetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        splashScreen()
        observeNetworkConnection(context = this)

        setContent {
            OskarDemoRootSurface()
        }
    }

    private fun splashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition { mainViewModel.isSplashScreenLoading.value }
        }
    }

    @Composable
    private fun OskarDemoRootSurface() {
        val rootNavController = rememberNavController()
        
        val startDestination by mainViewModel.postSplashDestination.collectAsStateWithLifecycle()

        OskarDemoProjectTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (startDestination.isNotBlank()) {
                    SetupRootNavGraph(
                        rootNavController = rootNavController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}