package com.billboard.movies.data.local.entity

data class ActorEntity(
    val id: Int,
    var name: String? = null,
    var character: String? = null,
    var profilePath: String? = null
)