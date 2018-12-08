package com.billboard.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.billboard.movies.model.db.entity.*
import com.billboard.movies.model.repository.MovieRepository
import com.billboard.movies.model.rest.base.Resource
import javax.inject.Inject

class MovieDetailViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovie(movieId: Int): LiveData<Movie> {
        return movieRepository.getMovie(movieId)
    }

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetail>> {
        return movieRepository.getMovieDetail(movieId)
    }

    fun getMovieCast(movieId: Int): LiveData<Resource<List<Actor>>> {
        return movieRepository.getCast(movieId)
    }

    fun getVideos(movieId: Int): LiveData<Resource<List<Video>>> {
        return movieRepository.getVideos(movieId)
    }

    fun getLanguage(lang: String): LiveData<Language?> {
        return movieRepository.getLanguage(lang)
    }

    fun getGenres(movieId: Int): LiveData<List<Genre>>  {
        return movieRepository.getGenres(movieId)
    }

}
