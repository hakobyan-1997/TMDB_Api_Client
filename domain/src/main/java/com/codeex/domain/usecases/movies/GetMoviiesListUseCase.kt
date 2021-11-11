package com.codeex.domain.usecases.movies

import com.codeex.domain.model.MovieModel
import com.codeex.domain.model.State
import com.codeex.domain.usecases.base.BaseUseCase

interface GetMoviesListUseCase : BaseUseCase<Map<String, String>, List<MovieModel>> {
    override suspend operator fun invoke(param: Map<String, String>): State<List<MovieModel>>
}