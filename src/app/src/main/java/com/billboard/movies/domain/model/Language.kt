package com.billboard.movies.domain.model

import com.billboard.movies.data.local.entity.LanguageEntity

data class Language(
    var format6391: String? = null,
    var name: String? = null,
    var resourceId: Int? = null
) {
    override fun equals(other: Any?): Boolean {

        if (other is LanguageEntity) {
            return other.format6391 == format6391
        }

        return super.equals(other)
    }
}
