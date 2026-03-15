package com.example.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS popular_movies_cache (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                posterPath TEXT NOT NULL,
                overview TEXT NOT NULL,
                releaseDate TEXT NOT NULL,
                voteAverage REAL NOT NULL,
                voteCount INTEGER NOT NULL
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS discover_page_cache (
                startDate TEXT NOT NULL,
                endDate TEXT NOT NULL,
                page INTEGER NOT NULL,
                id INTEGER NOT NULL,
                title TEXT NOT NULL,
                posterPath TEXT NOT NULL,
                overview TEXT NOT NULL,
                releaseDate TEXT NOT NULL,
                voteAverage REAL NOT NULL,
                voteCount INTEGER NOT NULL,
                PRIMARY KEY(startDate, endDate, page, id)
            )
        """.trimIndent())
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE movies RENAME COLUMN posterPath TO posterUrl")
        db.execSQL("ALTER TABLE popular_movies_cache RENAME COLUMN posterPath TO posterUrl")
        db.execSQL("ALTER TABLE discover_page_cache RENAME COLUMN posterPath TO posterUrl")
    }
}
