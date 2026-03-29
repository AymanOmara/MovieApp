package com.example.data.network

import com.example.data.network.dto.BaseResponse
import com.example.data.network.dto.CreditsDto
import com.example.data.network.dto.MovieDetailsDto
import com.example.data.network.dto.MovieDto
import com.example.data.utils.ApiEndpoints
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesWebServices {

    @GET(ApiEndpoints.MOVIE_POPULAR)
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): BaseResponse<List<MovieDto>>

    @GET(ApiEndpoints.DISCOVER_MOVIE)
    suspend fun discoverMoviesByDateRange(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): BaseResponse<List<MovieDto>>

    @GET(ApiEndpoints.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailsDto

    @GET(ApiEndpoints.MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): CreditsDto

    @GET(ApiEndpoints.MOVIE_SIMILAR)
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): BaseResponse<List<MovieDto>>
}
