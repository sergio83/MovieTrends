package com.billboard.movies.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(
    tableName = "movies",
    indices = [Index(value = ["id"], unique = true)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) @Expose val primaryId: Int,
    var id: Int = 0,
    var voteAverage: Double = 0.0,
    var title: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var genreIds: List<Int> = listOf(),
    var releaseDate: String? = null,
    var originalLanguage: String? = null,
    var posterPath: String? = null,
    var widePosterPath: String? = null
)