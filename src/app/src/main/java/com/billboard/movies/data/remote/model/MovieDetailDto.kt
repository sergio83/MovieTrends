package com.billboard.movies.data.remote.model

data class MovieDetailDto(
    var homepage: String? = null,
    var status: String? = null,
    var runtime: Int = 0,
    var budget: Int = 0
)