package com.billboard.movies.data.local.entity

data class LanguageEntity(
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
