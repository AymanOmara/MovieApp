package com.example.di

import com.example.data.repository.MovieCatalogRepositoryImpl
import com.example.data.repository.MovieDetailsRepositoryImpl
import com.example.data.repository.WatchlistRepositoryImpl
import com.example.domain.repository.MovieCatalogRepository
import com.example.domain.repository.MovieDetailsRepository
import com.example.domain.repository.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(impl: WatchlistRepositoryImpl): WatchlistRepository

    @Binds
    @Singleton
    abstract fun bindMovieCatalogRepository(impl: MovieCatalogRepositoryImpl): MovieCatalogRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository
}
