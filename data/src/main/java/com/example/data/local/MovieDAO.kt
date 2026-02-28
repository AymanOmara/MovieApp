package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Insert
    fun insert(movie: MovieLocal)

    @Delete
    fun delete(movie: MovieLocal)

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<MovieLocal>>

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :id)")
    fun isMovieStored(id: Int): Flow<Int>
}
