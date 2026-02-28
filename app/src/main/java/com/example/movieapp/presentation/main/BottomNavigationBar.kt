package com.example.movieapp.presentation.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.presentation.navigation.Screen
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Primary

@Composable
fun BottomNavigationBar(
    currentScreen: NavKey?,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Primary
    ) {
        Screen.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = stringResource(screen.titleResId)) },
                label = { Text(stringResource(screen.titleResId), color = Color.White) },
                selected = currentScreen == screen,
                onClick = { onNavigate(screen) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    MovieAppTheme {
        BottomNavigationBar(
            currentScreen = Screen.Home,
            onNavigate = {}
        )
    }
}
