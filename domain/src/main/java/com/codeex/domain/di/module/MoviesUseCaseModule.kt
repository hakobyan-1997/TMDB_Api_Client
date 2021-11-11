package com.codeex.domain.di.module

import com.codeex.domain.repository.MoviesRepo
import com.codeex.domain.usecases.movies.GetMoviesListUseCase
import com.codeex.domain.usecases.movies.GetMoviesListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MoviesUseCaseModule {

    @Provides
    fun bindMoviesListUseCase(repository: MoviesRepo): GetMoviesListUseCase {
        return GetMoviesListUseCaseImpl(repository)
    }
}