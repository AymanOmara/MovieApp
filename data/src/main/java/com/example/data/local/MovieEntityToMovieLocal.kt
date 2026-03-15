package com.example.data.local

import com.example.domain.entity.Movie

fun Movie.movieEntityToMovieLocal(): MovieLocal {
    return MovieLocal(
        id = id,
        title = title,
        posterUrl = posterUrl,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}