package com.billboard.movies.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.billboard.movies.di.base.ApplicationContext
import com.billboard.movies.model.db.dao.GenreDao
import com.billboard.movies.model.db.dao.MoviesDao
import com.billboard.movies.model.db.entity.*
import com.billboard.movies.model.rest.ApiClient
import com.billboard.movies.model.rest.base.*
import com.billboard.movies.model.rest.entity.CastResponse
import com.billboard.movies.model.rest.entity.GenreResponse
import com.billboard.movies.model.rest.entity.MovieResponse
import com.billboard.movies.model.rest.entity.VideosResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class MovieRepository @Inject constructor(
    val services: ApiClient,
    val appExecutors: AppExecutors,
    @ApplicationContext val context: Context,
    val genreDao: GenreDao,
    val movieDao: MoviesDao
) {

    private val pageSize = 20

    fun getMovie(movieId: Int): LiveData<Movie> {
        return movieDao.getMovie(movieId)
    }


    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return MediatorLiveData<Resource<List<Movie>>>().apply {
            var lastA: Resource<List<Movie>>? = null
            var lastB: Resource<List<Genre>>? = null

            fun update() {

                if (lastA != null && lastB != null) {

                    if (lastA?.status == Status.LOADING || lastB?.status == Status.LOADING) {
                        this.value = Resource.loading()
                    } else if (lastA?.status == Status.SUCCESS || lastB?.status == Status.SUCCESS) {

                        lastA?.let{
                            if(it.data != null)
                                movieDao.addMoviesGenres(it.data)
                        }

                        this.value = lastA
                    } else {
                        this.value = lastA
                    }

                }

            }

            addSource(getMovies2()) {
                lastA = it
                update()
            }
            addSource(getGenres()) {
                lastB = it
                update()
            }
        }

    }

    private fun getMovies2(): LiveData<Resource<List<Movie>>> {

        return object : NetworkBoundResource<List<Movie>, MovieResponse>(appExecutors) {
            override fun saveCallResult(item: MovieResponse) {
                movieDao.updateMovies(item.movies)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return true//data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.loadAllMovies()
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                return services.getMovies()
            }

        }.asLiveData()
    }

    fun getNextMoviesPage(): LiveData<Resource<List<Movie>>> {

        return object : NetworkBoundResource<List<Movie>, MovieResponse>(appExecutors) {
            override fun saveCallResult(item: MovieResponse) {
                movieDao.updateMovies(item.movies)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.loadAllMovies()
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {

                val count = movieDao.getMoviesCount()
                val nextPage = when (count) {
                    0 -> 1
                    else -> count / pageSize + 1
                }

                return services.getMovies(nextPage)
            }

        }.asLiveData()
    }

    private fun getGenres(): LiveData<Resource<List<Genre>>> {
        return object : NetworkBoundResource<List<Genre>, GenreResponse>(appExecutors) {
            override fun saveCallResult(item: GenreResponse) {
                genreDao.insertGenres(item.genres)
            }

            override fun shouldFetch(data: List<Genre>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Genre>> {
                return genreDao.loadAllGenres()
            }

            override fun createCall(): LiveData<ApiResponse<GenreResponse>> {
                return services.getGenres()
            }

        }.asLiveData()
    }

    fun getGenres(movieId: Int): LiveData<List<Genre>> {
        return genreDao.getGenresOfMovie(movieId)
    }

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetail>> {
        return object : NetworkOnlyBoundResource<MovieDetail, MovieDetail>(appExecutors) {
            override fun processResult(item: MovieDetail?): MovieDetail? {
                return item
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetail>> {
                return services.getMovieDetail(movieId)
            }

        }.asLiveData()
    }

    fun getCast(movieId: Int): LiveData<Resource<List<Actor>>> {
        return object : NetworkOnlyBoundResource<List<Actor>, CastResponse>(appExecutors) {

            override fun processResult(item: CastResponse?): List<Actor>? {
                return item?.actors
            }

            override fun createCall(): LiveData<ApiResponse<CastResponse>> {
                return services.getCast(movieId)
            }

        }.asLiveData()
    }

    fun getVideos(movieId: Int): LiveData<Resource<List<Video>>> {
        return object : NetworkOnlyBoundResource<List<Video>, VideosResponse>(appExecutors) {

            override fun processResult(item: VideosResponse?): List<Video>? {
                return item?.videos
            }

            override fun createCall(): LiveData<ApiResponse<VideosResponse>> {
                return services.getMovieTrailers(movieId)
            }

        }.asLiveData()
    }

    fun getLanguage(iso639: String): MutableLiveData<Language?> {
        return MutableLiveData<Language?>().apply {
            appExecutors.diskIO().execute {
                val fileName = "iso_639-1.json"
                val json = context.assets.open(fileName).bufferedReader().use {
                    it.readText()
                }

                val languages: Map<String, Language> =
                    Gson().fromJson(json, object : TypeToken<Map<String, Language>>() {}.type)
                appExecutors.mainThread().execute {
                    this.value = languages[iso639]
                }
            }
        }

    }

}

