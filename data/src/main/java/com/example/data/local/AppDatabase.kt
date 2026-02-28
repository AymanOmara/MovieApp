package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieLocal::class,
        PopularMovieCache::class,
        DiscoverPageCacheItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
    abstract fun popularMoviesCacheDao(): PopularMoviesCacheDao
    abstract fun discoverPageCacheDao(): DiscoverPageCacheDao
}