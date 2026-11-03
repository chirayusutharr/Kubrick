package com.example.kubrick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backdrop = rememberLayerBackdrop()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedTab = when (currentRoute) {
        "watchlist" -> 1
        "watch-party" -> 2
        else -> 0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
        ) {
            composable("home") {
                HomeTabScreen()
            }

            composable("watchlist") {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your watchlist", color = Color.White)
                }
            }

            composable("watch-party") {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Watch Party", color = Color.White)
                }
            }
        }

        GlassBottomBar(
            selectedTab = selectedTab,
            backdrop = backdrop,
            onTabSelected = { tab ->
                val route = when (tab) {
                    1 -> "watchlist"
                    2 -> "watch-party"
                    else -> "home"
                }

                navController.navigate(route) {
                    popUpTo("home") {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}