package com.billboard.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.billboard.movies.model.controller.SettingsController
import com.billboard.movies.model.db.entity.Language
import com.billboard.movies.model.db.entity.Movie
import com.billboard.movies.model.repository.MovieRepository
import com.billboard.movies.model.rest.base.Resource
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movieRepository.getMovies()
    }

    fun getNextMoviePage(): LiveData<Resource<List<Movie>>> {
        return movieRepository.getNextMoviesPage()
    }

    fun getLanguages(): List<Language> {
        return SettingsController.getLanguages()
    }

    fun getCurrentLanguage(): Language {
        return SettingsController.getCurrentLanguage()
    }

    fun saveLanguage(lang: Language) {
        SettingsController.saveLanguage(lang)
    }

}
