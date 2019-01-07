package com.billboard.movies.data.local.entity

data class VideoEntity(
    val id: String,
    var key: String? = null,
    var type: String? = null,
    var site: String? = null,
    var name: String? = null,
    var size: Int = 0
)
