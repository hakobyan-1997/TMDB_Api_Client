package com.codeex.domain.usecases.movies

import com.codeex.domain.repository.MoviesRepo
import javax.inject.Inject

class GetMoviesListUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepo) :
    GetMoviesListUseCase {
    override suspend operator fun invoke(param: Map<String, String>) =
        moviesRepository.getMoviesList(param)
}