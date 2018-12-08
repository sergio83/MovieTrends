package com.billboard.movies.model.rest.entity

import androidx.annotation.Nullable
import com.billboard.movies.model.db.entity.Genre
import com.google.gson.annotations.SerializedName

class GenreResponse {

    @Nullable
    @SerializedName("genres")
    var genres: List<Genre> = listOf()
}