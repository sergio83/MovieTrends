package com.billboard.movies.presentation.ui.billboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.billboard.movies.R
import com.billboard.movies.domain.model.Movie
import com.billboard.movies.presentation.ui.common.view.PopularityView
import com.billboard.movies.presentation.utils.extensions.parse
import com.facebook.drawee.view.SimpleDraweeView


class MoviesAdapter(private var mList: MutableList<Movie> = mutableListOf(), private val listener: (Movie, View) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ItemViewType {
        MOVIE, LOADING
    }

    private var showLoading = true

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val subtitleTextView: TextView = itemView.findViewById(R.id.subtitle)
        val bannerImageView: SimpleDraweeView = itemView.findViewById(R.id.banner)
        val popularityView: PopularityView = itemView.findViewById(R.id.popularity)


        fun bind(item: Movie, listener: (Movie, View) -> Unit) {
            titleTextView.text = item.title
            subtitleTextView.text = item.releaseDate?.parse("yyyy-MM-dd", "MMMM dd, yyyy")?.capitalize()
            popularityView.percentage = (item.voteAverage * 10).toInt()
            bannerImageView.setImageURI(item.bannerUri())

            itemView.setOnClickListener {
                listener(item, itemView)
            }
        }

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun refreshList(list: List<Movie>) {

        val newMovieList = ArrayList<Movie>()
        newMovieList.addAll(list)

        val diffResult =
            DiffUtil.calculateDiff(
                MovieDiffUtilCallback(
                    this.mList,
                    newMovieList
                )
            )
        this.mList.clear()
        this.mList.addAll(list)
        diffResult.dispatchUpdatesTo(this)

    }

    fun size(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ItemViewType.LOADING.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.loading_item_view, parent, false)
                LoadingViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_view, parent, false)
                MovieViewHolder(view)
            }
        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            holder.bind(mList[position], listener)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (showLoading) mList.size + 1 else mList.size
    }

    override fun getItemViewType(position: Int): Int {

        if (showLoading && mList.size == position) {
            return ItemViewType.LOADING.ordinal
        }

        return ItemViewType.MOVIE.ordinal
    }
}