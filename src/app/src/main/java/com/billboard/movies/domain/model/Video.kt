package com.billboard.movies.domain.model

data class Video(
    val id: String,
    var key: String? = null,
    var type: String? = null,
    var site: String? = null,
    var name: String? = null,
    var size: Int = 0
)