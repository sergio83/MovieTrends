package com.billboard.movies.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.billboard.movies.R
import com.billboard.movies.data.local.entity.LanguageEntity
import com.billboard.movies.data.local.sharedPreference.AppPreferences
import com.billboard.movies.data.remote.api.utils.AppExecutors
import com.billboard.movies.presentation.di.base.ApplicationContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    val appExecutors: AppExecutors,
    @ApplicationContext val context: Context
) {


    internal fun getAllLanguages(): Map<String, LanguageEntity> {
        val fileName = "iso_639-1.json"
        val json = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }

        val languages: Map<String, LanguageEntity> =
            Gson().fromJson(json, object : TypeToken<Map<String, LanguageEntity>>() {}.type)
        return languages
    }

    fun getLanguage(iso639: String): MutableLiveData<LanguageEntity?> {
        return MutableLiveData<LanguageEntity?>().apply {
            appExecutors.diskIO().execute {
                val languages = getAllLanguages()
                appExecutors.mainThread().execute {
                    this.value = languages[iso639]
                }
            }
        }
    }

    fun getSelectableLanguages(): List<LanguageEntity> {
        return listOf(LanguageEntity("en", resourceId = R.string.en), LanguageEntity("es", resourceId = R.string.es))
    }

    fun getCurrentLanguage(): LanguageEntity {

        val lang = AppPreferences.language ?: Locale.getDefault().language
        for (it in getSelectableLanguages()) {
            if (it.format6391 == lang) {
                return it
            }
        }

        return getSelectableLanguages().first()
    }

    fun saveLanguage(lang: LanguageEntity) {
        AppPreferences.language = lang.format6391
    }
}