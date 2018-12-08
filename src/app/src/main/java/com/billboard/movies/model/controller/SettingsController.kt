package com.billboard.movies.model.controller

import com.billboard.movies.R
import com.billboard.movies.model.db.entity.Language
import java.util.*

class SettingsController {

    companion object {
        fun getLanguages(): List<Language> {
            return listOf(Language("en", R.string.en), Language("es", R.string.es))
        }

        fun getCurrentLanguage(): Language {

            val lang = AppPreferences.language ?: Locale.getDefault().language
            for (it in getLanguages()) {
                if (it.format6391 == lang) {
                    return it
                }
            }

            return getLanguages().first()
        }

        fun saveLanguage(lang: Language) {
            AppPreferences.language = lang.format6391
        }

    }
}