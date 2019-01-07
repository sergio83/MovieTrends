package com.billboard.movies.presentation.ui.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.billboard.movies.data.remote.api.utils.Resource
import com.billboard.movies.domain.gateway.LanguageGateway
import com.billboard.movies.domain.gateway.MovieGateway
import com.billboard.movies.domain.model.*
import javax.inject.Inject

class MovieDetailViewModel
@Inject constructor(private val movieGateway: MovieGateway, private val languageGateway: LanguageGateway) : ViewModel() {

    fun getMovie(movieId: Int): LiveData<Movie> {
        return movieGateway.getMovie(movieId)
    }

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetail>> {
        return movieGateway.getMovieDetail(movieId)
    }

    fun getMovieCast(movieId: Int): LiveData<Resource<List<Actor>>> {
        return movieGateway.getCast(movieId)
    }

    fun getVideos(movieId: Int): LiveData<Resource<List<Video>>> {
        return movieGateway.getVideos(movieId)
    }

    fun getLanguage(lang: String): LiveData<Language?> {
        return languageGateway.getLanguage(lang)
    }

    fun getGenres(movieId: Int): LiveData<List<Genre>>  {
        return movieGateway.getGenres(movieId)
    }

}
