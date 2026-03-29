package com.example.data.network.dto

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDtoInstrumentedTest {

    @Test
    fun toMovie_mapsAllFields() {
        val dto = MovieDto(
            id = 1,
            title = "Title",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            overview = "Overview",
            releaseDate = "2024-01-01",
            voteAverage = 8.5,
            voteCount = 100
        )
        val movie = dto.toMovie()
        assertEquals(1, movie.id)
        assertEquals("Title", movie.title)
        assertTrue(movie.posterUrl.endsWith("poster.jpg"))
        assertTrue(movie.posterUrl.startsWith(Constants.IMAGE_BASE_URL))
        assertEquals("Overview", movie.overview)
        assertEquals("2024-01-01", movie.releaseDate)
        assertEquals(8.5, movie.voteAverage, 0.0)
        assertEquals(100, movie.voteCount)
    }
}
