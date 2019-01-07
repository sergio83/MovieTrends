package com.billboard.movies.data.gateway

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.billboard.movies.data.gateway.mapper.LanguageMapper
import com.billboard.movies.data.repository.LanguageRepository
import com.billboard.movies.domain.gateway.LanguageGateway
import com.billboard.movies.domain.model.Language

class LanguageGatewayImpl(private val languageRepository: LanguageRepository) : LanguageGateway {

    val mapper = LanguageMapper()

    override fun getLanguage(iso639: String): LiveData<Language?> {
        return Transformations.map(languageRepository.getLanguage(iso639)) { lang ->
            lang?.let{mapper.toModel(it)}
        }
    }

    override fun saveLanguage(lang: Language) {
        languageRepository.saveLanguage(mapper.toModel(lang))
    }

    override fun getCurrentLanguage(): Language {
        return languageRepository.getCurrentLanguage().let { mapper.toModel(it) }
    }

    override fun getSelectableLanguages(): List<Language> {
        return languageRepository.getSelectableLanguages().map { mapper.toModel(it) }
    }

}