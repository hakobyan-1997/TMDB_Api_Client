package com.codeex.domain.repository

import com.codeex.domain.model.MovieModel
import com.codeex.domain.model.State

interface MoviesRepo {
    suspend fun getMoviesList(param: Map<String, String>): State<List<MovieModel>>
}