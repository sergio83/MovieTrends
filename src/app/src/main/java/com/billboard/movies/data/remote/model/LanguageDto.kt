package com.billboard.movies.data.remote.model

import com.billboard.movies.data.local.entity.LanguageEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LanguageDto {

    @SerializedName("639-1")
    var format6391: String? = null
    var name: String? = null

    @Expose
    var resourceId: Int? = null

    constructor(format6391: String, resourceId: Int) {
        this.format6391 = format6391
        this.resourceId = resourceId
    }

    override fun equals(other: Any?): Boolean {

        if (other is LanguageEntity) {
            return other.format6391 == format6391
        }

        return super.equals(other)
    }
}
