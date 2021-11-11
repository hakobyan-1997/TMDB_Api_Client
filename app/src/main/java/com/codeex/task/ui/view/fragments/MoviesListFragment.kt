package  com.codeex.task.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeex.task.R
import com.codeex.task.base.fragments.BaseFragment
import com.codeex.task.base.navigator.screens.Screens
import com.codeex.task.ui.view.adapters.MoviesListAdapter
import com.codeex.task.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment : BaseFragment(R.layout.fragment_movies_list) {

    private val mMoviesViewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var moviesAdapter: MoviesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesAdapter()
        fetchMoviesList()
    }

    private fun initMoviesAdapter() {

        moviesAdapter.addOnItemClickListener {
            val mBundle = Bundle()
            mBundle.putSerializable("movie", it)
            openFragment(Screens.MOVIE_DETAIL, bundle = mBundle, recreate = true)
        }
        requireView().findViewById<RecyclerView>(R.id.moviesRecyclerView).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesAdapter
        }
    }

    /**
     * Getting Movies data from TMDB server
     */
    private fun fetchMoviesList() {
        mMoviesViewModel.moviesListPagingLiveData.observe(viewLifecycleOwner) {
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}