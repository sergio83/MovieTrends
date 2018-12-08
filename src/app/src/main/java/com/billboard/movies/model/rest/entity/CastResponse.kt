package com.billboard.movies.model.rest.entity

import com.billboard.movies.model.db.entity.Actor
import com.google.gson.annotations.SerializedName

class CastResponse {

    @SerializedName("cast")
    var actors: List<Actor> = listOf()
}