package com.billboard.movies.data.remote.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class VideosDto(
    @Nullable
    @SerializedName("results")
    var videos: List<VideoDto> = listOf()
)

data class VideoDto(
    val id: String,
    var key: String? = null,
    var type: String? = null,
    var site: String? = null,
    var name: String? = null,
    var size: Int = 0
)