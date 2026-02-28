package com.example.movieapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                title()
            }
        },
        navigationIcon = navigationIcon ?: {},
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieTopAppBarPreview() {
    MovieAppTheme {
        MovieTopAppBar(title = { Text("Movie Title", color = Color.White) })
    }
}
