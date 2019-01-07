package com.billboard.movies.data.remote.api

import androidx.lifecycle.LiveData
import com.billboard.movies.data.remote.api.utils.ApiResponse
import com.billboard.movies.data.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClient{

    //DOC: https://developers.themoviedb.org/3/movies/get-movie-credits

    @GET("3/movie/now_playing")
    fun getMovies(@Query("page") page: Int = 1): LiveData<ApiResponse<MoviesDto>>

    @GET("3/movie/{movieId}")
    fun getMovieDetail(@Path("movieId") movieId: Int): LiveData<ApiResponse<MovieDetailDto>>

    @GET("3/movie/{movieId}/videos")
    fun getMovieTrailers(@Path("movieId") movieId: Int): LiveData<ApiResponse<VideosDto>>

    @GET("3/movie/{movieId}/credits")
    fun getCast(@Path("movieId") movieId: Int): LiveData<ApiResponse<CastDto>>

    @GET("3/genre/movie/list")
    fun getGenres(): LiveData<ApiResponse<GenresDto>>
}