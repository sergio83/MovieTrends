package com.billboard.movies.model.rest.entity

import androidx.annotation.Nullable
import com.billboard.movies.model.db.entity.Video
import com.google.gson.annotations.SerializedName

class VideosResponse {
    @Nullable
    @SerializedName("results")
    var videos: List<Video> = listOf()
}