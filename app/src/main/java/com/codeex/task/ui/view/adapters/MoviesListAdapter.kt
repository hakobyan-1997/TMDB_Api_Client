package com.codeex.task.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.codeex.domain.model.MovieModel
import com.codeex.task.R
import com.codeex.task.android.ui.view.viewholder.MovieViewHolder
import javax.inject.Inject

class MoviesListAdapter @Inject constructor() :
    PagingDataAdapter<MovieModel, MovieViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: (item: MovieModel) -> Unit = {}
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.onBind(movieItem, onItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieViewHolder(view)
    }

    /**
     *  Adding Item click listener
     */
    fun addOnItemClickListener(onItemClickListener: (item: MovieModel) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    companion object {
        val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldConcert: MovieModel,
                newConcert: MovieModel
            ) = oldConcert.hashCode() == newConcert.hashCode()

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldConcert: MovieModel,
                newConcert: MovieModel
            ) = oldConcert == newConcert
        }
    }
}