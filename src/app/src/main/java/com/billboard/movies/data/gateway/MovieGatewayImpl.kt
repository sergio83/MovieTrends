package com.billboard.movies.data.gateway

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.billboard.movies.data.gateway.mapper.MovieMapper
import com.billboard.movies.data.remote.api.utils.Resource
import com.billboard.movies.data.repository.MovieRepository
import com.billboard.movies.domain.gateway.MovieGateway
import com.billboard.movies.domain.model.*

class MovieGatewayImpl(private val movieRepository: MovieRepository) :
    MovieGateway {

    private val mapper = MovieMapper()

    override fun getMovies(): LiveData<Resource<List<Movie>>> {
        return Transformations.map(movieRepository.getMovies()) { resource ->
            Resource<List<Movie>>(resource.status, resource.data?.map { mapper.toModel(it) }, resource.message)
        }
    }

    override fun getMovie(movieId: Int): LiveData<Movie> {
        return Transformations.map(movieRepository.getMovie(movieId)) { movie ->
            mapper.toModel(movie)
        }
    }

    override fun getNextMoviesPage(): LiveData<Resource<List<Movie>>> {
        return Transformations.map(movieRepository.getNextMoviesPage()) { resource ->
            Resource<List<Movie>>(resource.status, resource.data?.map { mapper.toModel(it) }, resource.message)
        }
    }

    override fun getGenres(movieId: Int): LiveData<List<Genre>> {
        return Transformations.map(movieRepository.getGenres(movieId)) { genres ->
            genres.map { mapper.toModel(it) }
        }
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetail>> {
        return Transformations.map(movieRepository.getMovieDetail(movieId)) { resource ->
            Resource<MovieDetail>(resource.status, resource.data?.let { mapper.toModel(it) }, resource.message)
        }
    }

    override fun getCast(movieId: Int): LiveData<Resource<List<Actor>>> {
        return Transformations.map(movieRepository.getCast(movieId)) { resource ->
            Resource<List<Actor>>(resource.status, resource.data?.map { mapper.toModel(it) }, resource.message)
        }
    }

    override fun getVideos(movieId: Int): LiveData<Resource<List<Video>>> {
        return Transformations.map(movieRepository.getVideos(movieId)) { resource ->
            Resource<List<Video>>(resource.status, resource.data?.map { mapper.toModel(it) }, resource.message)
        }
    }

}