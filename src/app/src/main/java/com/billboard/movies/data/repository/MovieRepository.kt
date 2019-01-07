package com.billboard.movies.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.billboard.movies.data.local.dao.GenreDao
import com.billboard.movies.data.local.dao.MoviesDao
import com.billboard.movies.data.local.entity.*
import com.billboard.movies.data.remote.api.ApiClient
import com.billboard.movies.data.remote.api.utils.*
import com.billboard.movies.data.remote.model.*
import com.billboard.movies.data.repository.mapper.LocalMapper
import com.billboard.movies.presentation.di.base.ApplicationContext
import javax.inject.Inject


class MovieRepository @Inject constructor(
    val services: ApiClient,
    val appExecutors: AppExecutors,
    @ApplicationContext val context: Context,
    val genreDao: GenreDao,
    val movieDao: MoviesDao,
    val mapper: LocalMapper
) {

    private val pageSize = 20

    fun getMovie(movieId: Int): LiveData<MovieEntity> {
        return movieDao.getMovie(movieId)
    }


    fun getMovies(): LiveData<Resource<List<MovieEntity>>> {
        return MediatorLiveData<Resource<List<MovieEntity>>>().apply {
            var lastA: Resource<List<MovieEntity>>? = null
            var lastB: Resource<List<GenreEntity>>? = null

            fun update() {

                if (lastA != null && lastB != null) {

                    if (lastA?.status == Status.LOADING || lastB?.status == Status.LOADING) {
                        this.value = Resource.loading()
                    } else if (lastA?.status == Status.SUCCESS || lastB?.status == Status.SUCCESS) {

                        lastA?.let {
                            if (it.data != null)
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

    private fun getMovies2(): LiveData<Resource<List<MovieEntity>>> {

        return object : NetworkBoundResource<List<MovieEntity>, MoviesDto>(appExecutors) {
            override fun saveCallResult(item: MoviesDto) {
                movieDao.updateMovies(item.movies.map { mapper.toEntity(it) })
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return true//data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<MovieEntity>> {
                return movieDao.loadAllMovies()
            }

            override fun createCall(): LiveData<ApiResponse<MoviesDto>> {
                return services.getMovies()
            }

        }.asLiveData()
    }

    fun getNextMoviesPage(): LiveData<Resource<List<MovieEntity>>> {

        return object : NetworkBoundResource<List<MovieEntity>, MoviesDto>(appExecutors) {
            override fun saveCallResult(item: MoviesDto) {
                movieDao.updateMovies(item.movies.map { mapper.toEntity(it) })
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<MovieEntity>> {
                return movieDao.loadAllMovies()
            }

            override fun createCall(): LiveData<ApiResponse<MoviesDto>> {

                val count = movieDao.getMoviesCount()
                val nextPage = when (count) {
                    0 -> 1
                    else -> count / pageSize + 1
                }

                return services.getMovies(nextPage)
            }

        }.asLiveData()
    }

    private fun getGenres(): LiveData<Resource<List<GenreEntity>>> {
        return object : NetworkBoundResource<List<GenreEntity>, GenresDto>(appExecutors) {
            override fun saveCallResult(item: GenresDto) {
                genreDao.insertGenres(item.genres.map { mapper.toEntity(it) })
            }

            override fun shouldFetch(data: List<GenreEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<GenreEntity>> {
                return genreDao.loadAllGenres()
            }

            override fun createCall(): LiveData<ApiResponse<GenresDto>> {
                return services.getGenres()
            }

        }.asLiveData()
    }

    fun getGenres(movieId: Int): LiveData<List<GenreEntity>> {
        return genreDao.getGenresOfMovie(movieId)
    }

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetailEntity>> {
        return object : NetworkOnlyBoundResource<MovieDetailEntity, MovieDetailDto>(appExecutors) {
            override fun processResult(item: MovieDetailDto?): MovieDetailEntity? {
                return item?.let { mapper.toEntity(it) }
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailDto>> {
                return services.getMovieDetail(movieId)
            }

        }.asLiveData()
    }

    fun getCast(movieId: Int): LiveData<Resource<List<ActorEntity>>> {
        return object : NetworkOnlyBoundResource<List<ActorEntity>, CastDto>(appExecutors) {

            override fun processResult(item: CastDto?): List<ActorEntity>? {
                return item?.actors?.map { mapper.toEntity(it) }
            }

            override fun createCall(): LiveData<ApiResponse<CastDto>> {
                return services.getCast(movieId)
            }

        }.asLiveData()
    }

    fun getVideos(movieId: Int): LiveData<Resource<List<VideoEntity>>> {
        return object : NetworkOnlyBoundResource<List<VideoEntity>, VideosDto>(appExecutors) {

            override fun processResult(item: VideosDto?): List<VideoEntity>? {
                return item?.videos?.map {
                    mapper.toEntity(it)
                }
            }

            override fun createCall(): LiveData<ApiResponse<VideosDto>> {
                return services.getMovieTrailers(movieId)
            }

        }.asLiveData()
    }

}

