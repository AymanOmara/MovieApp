package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun ErrorSection(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorSectionPreview() {
    MovieAppTheme {
        ErrorSection(
            message = stringResource(R.string.error_generic),
            onRetry = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorSectionLongMessagePreview() {
    MovieAppTheme {
        ErrorSection(
            message = stringResource(R.string.error_connection),
            onRetry = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
