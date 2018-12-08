package com.billboard.movies.model.db.entity

import androidx.room.*
import com.billboard.movies.BuildConfig
import com.billboard.movies.model.db.converter.Converters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "movies",
    indices = [Index(value = ["id"], unique = true)]
)
class Movie(@PrimaryKey(autoGenerate = true) @Expose val primaryId: Int) {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0

    var title: String? = null

    @SerializedName("original_title")
    var originalTitle: String? = null

    var overview: String? = null

    @SerializedName("genre_ids")
    var genreIds: List<Int> = listOf()

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("original_language")
    var originalLanguage: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("backdrop_path")
    var widePosterPath: String? = null

    fun bannerUri(): String? {
        widePosterPath?.let {
            return BuildConfig.IMAGE_HOST + "/t/p/w780/" + widePosterPath
        }
        return null
    }

    fun posterUri(): String? {
        posterPath?.let {
            return BuildConfig.IMAGE_HOST + "/t/p/w780/" + posterPath
        }
        return null
    }

}