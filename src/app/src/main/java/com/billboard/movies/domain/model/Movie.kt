package com.billboard.movies.domain.model

import com.billboard.movies.BuildConfig


data class Movie(
    val localId: Int,
    var videoId: Int = 0,
    var voteAverage: Double = 0.0,
    var title: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var genreIds: List<Int> = listOf(),
    var releaseDate: String? = null,
    var originalLanguage: String? = null,
    var posterPath: String? = null,
    var widePosterPath: String? = null
) {
    fun bannerUri(): String? {
        widePosterPath?.let {
            return BuildConfig.IMAGE_HOST + "/t/p/w780/" + widePosterPath
        }
        return null
    }

    fun posterUri(): String? {
        posterPath?.let {
            return BuildConfig.IMAGE_HOST + "/t/p/w780/" + posterPath
        }
        return null
    }
}