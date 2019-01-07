package com.billboard.movies.data.remote.model

import androidx.annotation.Nullable
import com.billboard.movies.data.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @Nullable @SerializedName("results") var movies: List<MovieDto> = listOf(),
    @Nullable var page: Int = 0,
    @Nullable @SerializedName("total_pages") var totalPages: Int = 0
)

data class MovieDto(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("title") var title: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("genre_ids") var genreIds: List<Int> = listOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("backdrop_path") var widePosterPath: String? = null
)