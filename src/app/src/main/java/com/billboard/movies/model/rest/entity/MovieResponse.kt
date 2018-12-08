package com.billboard.movies.model.rest.entity

import androidx.annotation.Nullable
import com.billboard.movies.model.db.entity.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {

    @Nullable
    @SerializedName("results")
    var movies: List<Movie> = listOf()

    @Nullable
    var page: Int = 0

    @Nullable
    @SerializedName("total_pages")
    var totalPages: Int = 0
}