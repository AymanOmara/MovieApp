package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.shimmerEffect
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun ShimmerMovieHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.padding(top = 60.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(100.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        repeat(4) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth(if (it == 3) 0.7f else 1f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerMovieHeaderPreview() {
    MovieAppTheme {
        ShimmerMovieHeader()
    }
}
