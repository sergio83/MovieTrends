package com.billboard.movies.model.db.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.billboard.movies.model.db.entity.Movie
import com.billboard.movies.model.db.entity.MovieGenre

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovie(vararg movie: Movie)

    @Delete
    fun deleteMovie(vararg movie: Movie)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies ORDER BY primaryId")
    fun loadAllMovies(): LiveData<List<Movie>>

    @Query("SELECT COUNT(id) FROM movies")
    fun getMoviesCount(): Int

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM movies INNER JOIN movies_genres ON movies.id=movies_genres.movieId WHERE movies_genres.genreId=:genreId")
    fun getMoviesOfGenre(genreId: Int): LiveData<List<MovieGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGenreToMovie(movieGenre: MovieGenre)

    @Query("DELETE FROM movies_genres")
    fun deleteAllMoviesGenres()

    @Query("DELETE FROM movies_genres WHERE movieId = :movieId")
    fun deleteMoviesGenres(movieId: Int)

    @Transaction
    fun updateMovies(list: List<Movie>) {
        deleteAllMovies()
        deleteAllMoviesGenres()
        insertMovies(list)
    }

    @Transaction
    fun addMoviesGenres(list: List<Movie>) {
        list.map {
            val movieId = it.id
            it.genreIds.map { id ->
                try {
                    addGenreToMovie(MovieGenre(movieId, id))
                } catch (e: Exception) {

                }

            }
        }
    }
}