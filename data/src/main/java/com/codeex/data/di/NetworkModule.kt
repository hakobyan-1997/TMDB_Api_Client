package com.codeex.data.di

import com.codeex.data.api.ApiInterface
import com.codeex.data.base.Constants
import com.codeex.data.repository.MoviesRepositoryImpl
import com.codeex.domain.repository.MoviesRepo
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.Url.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    fun bindMoviesRepository(apiService: ApiInterface): MoviesRepo {
        return MoviesRepositoryImpl(apiService)
    }
}