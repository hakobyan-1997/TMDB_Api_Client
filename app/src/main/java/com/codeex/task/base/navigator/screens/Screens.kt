package com.codeex.task.base.navigator.screens

import androidx.fragment.app.Fragment
import com.codeex.task.ui.view.fragments.MovieDetailFragment
import com.codeex.task.ui.view.fragments.MoviesListFragment

enum class Screens {

    MOVIES_LIST {
        override val fragmentClass = MoviesListFragment::class.java
    },
    MOVIE_DETAIL {
        override val fragmentClass = MovieDetailFragment::class.java
    };

    abstract val fragmentClass: Class<out Fragment>
}