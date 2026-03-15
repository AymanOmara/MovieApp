package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.MIGRATION_1_2
import com.example.data.local.MIGRATION_2_3
import com.example.data.local.MovieDAO
import com.example.data.local.PopularMoviesCacheDao
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.repository.MoviesRepositoryImpl
import com.example.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository = impl

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }

    @Provides
    fun provideMovieDao(
        database: AppDatabase
    ): MovieDAO {
        return database.movieDao()
    }

    @Provides
    fun providePopularMoviesCacheDao(
        database: AppDatabase
    ): PopularMoviesCacheDao {
        return database.popularMoviesCacheDao()
    }

    @Provides
    fun provideDiscoverPageCacheDao(
        database: AppDatabase
    ): DiscoverPageCacheDao {
        return database.discoverPageCacheDao()
    }
}
