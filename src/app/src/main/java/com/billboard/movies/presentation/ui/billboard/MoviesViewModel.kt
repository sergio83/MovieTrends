package com.billboard.movies.presentation.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.billboard.movies.data.remote.api.utils.Resource
import com.billboard.movies.domain.gateway.LanguageGateway
import com.billboard.movies.domain.gateway.MovieGateway
import com.billboard.movies.domain.model.Language
import com.billboard.movies.domain.model.Movie
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val movieGateway: MovieGateway, private val languageGateway: LanguageGateway) : ViewModel() {

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movieGateway.getMovies()
    }

    fun getNextMoviePage(): LiveData<Resource<List<Movie>>> {
        return movieGateway.getNextMoviesPage()
    }

    fun getLanguages(): List<Language> {
        return languageGateway.getSelectableLanguages()
    }

    fun getCurrentLanguage(): Language {
        return languageGateway.getCurrentLanguage()
    }

    fun saveLanguage(lang: Language) {
        languageGateway.saveLanguage(lang)
    }

}
