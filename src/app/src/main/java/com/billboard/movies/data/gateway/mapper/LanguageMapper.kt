package com.billboard.movies.data.gateway.mapper

import com.billboard.movies.data.local.entity.LanguageEntity
import com.billboard.movies.domain.model.Language

class LanguageMapper {

    fun toModel(language: LanguageEntity) =
        Language(language.format6391, language.name, language.resourceId)

    fun toModel(language: Language) =
        LanguageEntity(language.format6391, language.name, language.resourceId)

}
