package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun ShimmerMovieRow(
    itemCount: Int = 5,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        userScrollEnabled = false
    ) {
        items(itemCount) {
            ShimmerMovieCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerMovieRowPreview() {
    MovieAppTheme {
        ShimmerMovieRow()
    }
}
