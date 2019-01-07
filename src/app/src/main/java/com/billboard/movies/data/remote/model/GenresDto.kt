package com.billboard.movies.data.remote.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class GenresDto(
    @Nullable @SerializedName("genres") var genres: List<GenreDto> = listOf()
)

data class GenreDto(val id: Int, val name: String)