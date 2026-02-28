package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun ShimmerCastRow(
    itemCount: Int = 6,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false
    ) {
        items(itemCount) {
            ShimmerCastCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerCastRowPreview() {
    MovieAppTheme {
        ShimmerCastRow()
    }
}
