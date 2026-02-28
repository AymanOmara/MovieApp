package com.example.movieapp.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.movieapp.presentation.navigation.MovieNavHost
import com.example.movieapp.presentation.navigation.Screen
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Primary

@Composable
fun MainScreen() {
    val backStack = rememberNavBackStack(Screen.Home)

    Scaffold(
        containerColor = Primary,
        bottomBar = {
            val currentScreen = backStack.lastOrNull()
            if (currentScreen !is Screen.MovieDetails) {
                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavigate = { screen -> backStack.navigateTo(screen) }
                )
            }
        }
    ) { innerPadding ->
        MovieNavHost(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private fun NavBackStack<NavKey>.navigateTo(screen: Screen) {
    if (lastOrNull() != screen) {
        clear()
        add(screen)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MovieAppTheme {
        MainScreen()
    }
}
