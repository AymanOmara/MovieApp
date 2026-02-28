package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
fun ShimmerCastCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerCastCardPreview() {
    MovieAppTheme {
        ShimmerCastCard(modifier = Modifier.padding(16.dp))
    }
}
