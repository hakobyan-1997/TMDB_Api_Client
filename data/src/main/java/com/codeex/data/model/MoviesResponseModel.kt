package com.codeex.data.model

import com.codeex.domain.model.MovieModel
import com.google.gson.annotations.SerializedName

data class MoviesResponseModel(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<MovieModel>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)