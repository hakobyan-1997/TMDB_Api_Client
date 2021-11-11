package com.codeex.task.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.codeex.domain.model.MovieModel
import com.codeex.task.R
import com.codeex.task.base.ext.toast
import com.codeex.task.base.fragments.BaseFragment

class MovieDetailFragment : BaseFragment(R.layout.fragment_movie_details) {

    private lateinit var movieItemModel: MovieModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getting movie model from argument
        getArgumentData()
        // Adding data to corresponding views
        addContent()
    }

    private fun addContent() {
        if (!this::movieItemModel.isInitialized) {
            toast("Can't show Movie")
            return
        }

        // Show cover image
        requireView().findViewById<ImageView>(R.id.movieCoverImage).apply {
            load("https://image.tmdb.org/t/p/w500/${movieItemModel.backdropPath}")
        }
        // Show overview text
        requireView().findViewById<TextView>(R.id.movieOverViewText).apply {
            text = movieItemModel.overview
        }
    }

    private fun getArgumentData() {
        if (arguments == null) return
        movieItemModel = requireArguments().getSerializable("movie") as MovieModel
    }
}