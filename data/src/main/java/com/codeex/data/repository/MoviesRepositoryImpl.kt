package com.codeex.data.repository

import com.codeex.data.api.ApiInterface
import com.codeex.domain.model.MovieModel
import com.codeex.domain.model.State
import com.codeex.domain.model.Success
import com.codeex.domain.repository.MoviesRepo
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiInterface) :
    MoviesRepo {
    override suspend fun getMoviesList(param: Map<String, String>): State<List<MovieModel>> {
        // Getting Server response
        val moviesResponse = apiService.getMoviesList(param)
        // Converting server response model to Movies Item list
        return Success(moviesResponse.body()?.results ?: listOf())
    }
}