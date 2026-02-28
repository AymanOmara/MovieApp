package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMoviesCacheDao {

    @Query("SELECT * FROM popular_movies_cache ORDER BY id")
    fun getAll(): Flow<List<PopularMovieCache>>

    @Query("SELECT * FROM popular_movies_cache ORDER BY id")
    suspend fun getAllOnce(): List<PopularMovieCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PopularMovieCache>)

    @Query("DELETE FROM popular_movies_cache")
    suspend fun deleteAll()
}
