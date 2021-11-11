package com.codeex.data.api

import com.codeex.data.model.MoviesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("movie/popular")
    suspend fun getMoviesList(
        @QueryMap queryMap: Map<String, String>
    ): Response<MoviesResponseModel>
}