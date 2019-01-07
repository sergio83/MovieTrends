package com.billboard.movies.domain.model

import com.billboard.movies.BuildConfig

data class Actor(
    val id: Int,
    var name: String? = null,
    var character: String? = null,
    var profilePath: String? = null
) {
    fun profileUri(): String? {
        profilePath?.let {
            return BuildConfig.IMAGE_HOST + "/t/p/w200/" + profilePath
        }
        return null
    }
}