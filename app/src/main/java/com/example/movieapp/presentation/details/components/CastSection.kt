package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.Cast
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun CastSection(
    cast: List<Cast>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.cast),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when {
            isLoading -> {
                ShimmerCastRow()
            }
            error != null && cast.isEmpty() -> {
                ErrorSection(
                    message = error,
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            cast.isEmpty() -> {
                Text(
                    text = stringResource(R.string.no_cast_available),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cast.take(20)) { castMember ->
                        CastCard(cast = castMember)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CastSectionLoadingPreview() {
    MovieAppTheme {
        CastSection(
            cast = emptyList(),
            isLoading = true,
            error = null,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CastSectionErrorPreview() {
    MovieAppTheme {
        CastSection(
            cast = emptyList(),
            isLoading = false,
            error = stringResource(R.string.error_cast),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CastSectionEmptyPreview() {
    MovieAppTheme {
        CastSection(
            cast = emptyList(),
            isLoading = false,
            error = null,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CastSectionContentPreview() {
    MovieAppTheme {
        CastSection(
            cast = Cast.previewList(),
            isLoading = false,
            error = null,
            onRetry = {}
        )
    }
}
