package com.codeex.task.ui.viewmodel.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codeex.domain.model.MovieModel
import com.codeex.domain.model.onFailure
import com.codeex.domain.model.onSuccess
import com.codeex.domain.usecases.movies.GetMoviesListUseCase

class MoviesListDataSource(private val moviesListUseCase: GetMoviesListUseCase) :
    PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        // generate pagination page value
        val page = params.key ?: 1
        val queryMap = LinkedHashMap<String, String>()
        queryMap["page"] = page.toString()
        queryMap["language"] = "en-US"
        queryMap["api_key"] = "73c1d8bd02cdcf8e590a1246f28de01a"
        var result: List<MovieModel> = ArrayList()
        moviesListUseCase(queryMap)
            .onSuccess {
                result = it
            }
            .onFailure {
                return LoadResult.Error(it.throwable)
            }
        // generate pagination next page value
        val nextPage: Int? = if (result.isNotEmpty()) page + 1 else null
        val prevPage: Int? = if (page == 0) null else page - 1
        // notify data to recycler view
        return LoadResult.Page(
            data = result,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        TODO("Not yet implemented")
    }
}