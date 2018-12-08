package com.billboard.movies.model.rest

import androidx.lifecycle.LiveData
import com.billboard.movies.model.db.entity.MovieDetail
import com.billboard.movies.model.rest.base.ApiResponse
import com.billboard.movies.model.rest.entity.CastResponse
import com.billboard.movies.model.rest.entity.GenreResponse
import com.billboard.movies.model.rest.entity.MovieResponse
import com.billboard.movies.model.rest.entity.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClient{

    //DOC: https://developers.themoviedb.org/3/movies/get-movie-credits

    @GET("3/movie/now_playing")
    fun getMovies(@Query("page") page: Int = 1): LiveData<ApiResponse<MovieResponse>>

    @GET("3/movie/{movieId}")
    fun getMovieDetail(@Path("movieId") movieId: Int): LiveData<ApiResponse<MovieDetail>>

    @GET("3/movie/{movieId}/videos")
    fun getMovieTrailers(@Path("movieId") movieId: Int): LiveData<ApiResponse<VideosResponse>>

    @GET("3/movie/{movieId}/credits")
    fun getCast(@Path("movieId") movieId: Int): LiveData<ApiResponse<CastResponse>>

    @GET("3/genre/movie/list")
    fun getGenres(): LiveData<ApiResponse<GenreResponse>>
}