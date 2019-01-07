package com.billboard.movies.domain.model

data class MovieDetail(
    var homepage: String? = null,
    var status: String? = null,
    var runtime: Int = 0,
    var budget: Int = 0
)