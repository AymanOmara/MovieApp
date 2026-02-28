package com.example.data.network.dto

import com.example.domain.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoTest {

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
        assertEquals("/poster.jpg", movie.posterPath)
        assertEquals("Overview", movie.overview)
        assertEquals("2024-01-01", movie.releaseDate)
        assertEquals(8.5, movie.voteAverage, 0.0)
        assertEquals(100, movie.voteCount)
    }

    @Test
    fun toMovie_handlesNullsWithDefaults() {
        val dto = MovieDto(
            id = 2,
            title = null,
            posterPath = null,
            backdropPath = null,
            overview = null,
            releaseDate = null,
            voteAverage = null,
            voteCount = null
        )
        val movie = dto.toMovie()
        assertEquals(2, movie.id)
        assertEquals("", movie.title)
        assertEquals("", movie.posterPath)
        assertEquals("", movie.overview)
        assertEquals("", movie.releaseDate)
        assertEquals(0.0, movie.voteAverage, 0.0)
        assertEquals(0, movie.voteCount)
    }
}
