package com.billboard.movies.data.remote.model

import com.billboard.movies.data.local.entity.ActorEntity
import com.google.gson.annotations.SerializedName

data class CastDto(
    @SerializedName("cast") var actors: List<ActorDto> = listOf()
)

data class ActorDto(
    val id: Int,
    var name: String? = null,
    var character: String? = null,
    @SerializedName("profile_path") var profilePath: String? = null
)