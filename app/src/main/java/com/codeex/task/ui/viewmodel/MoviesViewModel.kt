package com.codeex.task.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codeex.domain.model.MovieModel
import com.codeex.domain.usecases.movies.GetMoviesListUseCase
import com.codeex.task.ui.viewmodel.datasource.MoviesListDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesListUseCase: GetMoviesListUseCase
) : ViewModel() {

    val moviesListPagingLiveData =
        Pager(PagingConfig(pageSize = 20)) {
            MoviesListDataSource(moviesListUseCase)
        }.flow.cachedIn(CoroutineScope(Dispatchers.IO))
            .asLiveData()
            .let { it as MutableLiveData<PagingData<MovieModel>> }
}