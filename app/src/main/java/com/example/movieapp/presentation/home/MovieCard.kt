package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.domain.model.Movie
import com.example.domain.utils.Constants
import com.example.movieapp.ui.theme.Primary

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    showRemoveFromWatchlist: Boolean = false,
    onRemoveFromWatchlist: () -> Unit = {}
) {
    val posterUrl = movie.posterPath.let { path ->
        Constants.IMAGE_BASE_URL + path.trimStart('/')
    }
    Card(
        modifier = modifier.width(140.dp).height(260.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Primary),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(posterUrl)
                        .build(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }
            if (showRemoveFromWatchlist) {
                IconButton(
                    onClick = onRemoveFromWatchlist,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.remove_from_watchlist),
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    MaterialTheme {
        MovieCard(
            movie = Movie.preview()
        )
    }
}
