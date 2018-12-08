package com.billboard.movies.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.billboard.movies.model.db.entity.Movie

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
        return oldMovieList?.get(oldItemPosition)?.id == newMovieList?.get(newItemPosition)?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovieList?.get(oldItemPosition) == newMovieList?.get(newItemPosition)
    }
}