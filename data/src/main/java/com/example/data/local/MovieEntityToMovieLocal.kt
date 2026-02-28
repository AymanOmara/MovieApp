package com.example.data.local

import com.example.domain.model.Movie

fun Movie.movieEntityToMovieLocal(): MovieLocal {
    return MovieLocal(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}