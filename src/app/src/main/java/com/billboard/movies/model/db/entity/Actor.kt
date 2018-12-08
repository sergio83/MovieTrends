package com.billboard.movies.model.db.entity

import com.billboard.movies.BuildConfig
import com.google.gson.annotations.SerializedName

class Actor(val id: Int){

    var name:String? = null

    var character:String? = null

    @SerializedName("profile_path")
    var profilePath:String? = null

    fun profileUri(): String?{
        profilePath?.let{
            return BuildConfig.IMAGE_HOST + "/t/p/w200/" + profilePath
        }
        return null
    }

}