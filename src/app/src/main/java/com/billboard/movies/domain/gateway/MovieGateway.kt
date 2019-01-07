package com.billboard.movies.domain.gateway

import androidx.lifecycle.LiveData
import com.billboard.movies.data.remote.api.utils.Resource
import com.billboard.movies.domain.model.*

interface MovieGateway {

    fun getVideos(movieId: Int): LiveData<Resource<List<Video>>>
    fun getMovie(movieId: Int): LiveData<Movie>
    fun getMovies(): LiveData<Resource<List<Movie>>>
    fun getNextMoviesPage(): LiveData<Resource<List<Movie>>>
    fun getGenres(movieId: Int): LiveData<List<Genre>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetail>>
    fun getCast(movieId: Int): LiveData<Resource<List<Actor>>>
}