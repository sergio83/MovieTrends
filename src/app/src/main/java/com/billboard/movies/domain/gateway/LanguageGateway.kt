package com.billboard.movies.domain.gateway

import androidx.lifecycle.LiveData
import com.billboard.movies.domain.model.Language


interface LanguageGateway {

    fun getLanguage(iso639: String): LiveData<Language?>
    fun saveLanguage(lang: Language)
    fun getCurrentLanguage(): Language
    fun getSelectableLanguages(): List<Language>
}