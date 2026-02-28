package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.Movie

@Dao
interface DiscoverPageCacheDao {

    @Query(
        "SELECT * FROM discover_page_cache WHERE startDate = :startDate AND endDate = :endDate AND page = :page ORDER BY id"
    )
    suspend fun getPage(startDate: String, endDate: String, page: Int): List<DiscoverPageCacheItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(items: List<DiscoverPageCacheItem>)

    @Query("DELETE FROM discover_page_cache WHERE startDate = :startDate AND endDate = :endDate AND page = :page")
    suspend fun deletePage(startDate: String, endDate: String, page: Int)
}
