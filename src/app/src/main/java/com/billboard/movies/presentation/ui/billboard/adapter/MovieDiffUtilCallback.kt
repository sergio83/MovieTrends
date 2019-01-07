package com.billboard.movies.presentation.ui.billboard.adapter

import androidx.recyclerview.widget.DiffUtil
import com.billboard.movies.domain.model.Movie

class MovieDiffUtilCallback(
    private val oldMovieList: MutableList<Movie>?,
    private val newMovieList: MutableList<Movie>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldMovieList?.size!!
    }

    override fun getNewListSize(): Int {
        return newMovieList?.size!!
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovieList?.get(oldItemPosition)?.videoId == newMovieList?.get(newItemPosition)?.videoId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovieList?.get(oldItemPosition) == newMovieList?.get(newItemPosition)
    }
}