package com.example.data.network.dto

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoTest {

    /**
     * JVM unit test: avoids [com.example.data.utils.Constants.IMAGE_BASE_URL] / native Secrets
     * (poster path null → mapping skips CDN base). Full poster URL mapping is covered in
     * [MovieDtoInstrumentedTest].
     */
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
        assertEquals("", movie.posterUrl)
        assertEquals("", movie.overview)
        assertEquals("", movie.releaseDate)
        assertEquals(0.0, movie.voteAverage, 0.0)
        assertEquals(0, movie.voteCount)
    }
}
