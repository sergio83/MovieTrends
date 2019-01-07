package com.billboard.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.billboard.movies.data.local.entity.MovieEntity
import com.billboard.movies.data.local.entity.MovieGenreEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movies: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(vararg movie: MovieEntity)

    @Delete
    fun deleteMovie(vararg movie: MovieEntity)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies ORDER BY primaryId")
    fun loadAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT COUNT(id) FROM movies")
    fun getMoviesCount(): Int

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<MovieEntity>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM movies INNER JOIN movies_genres ON movies.id=movies_genres.movieId WHERE movies_genres.genreId=:genreId")
    fun getMoviesOfGenre(genreId: Int): LiveData<List<MovieGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGenreToMovie(movieGenre: MovieGenreEntity)

    @Query("DELETE FROM movies_genres")
    fun deleteAllMoviesGenres()

    @Query("DELETE FROM movies_genres WHERE movieId = :movieId")
    fun deleteMoviesGenres(movieId: Int)

    @Transaction
    fun updateMovies(list: List<MovieEntity>) {
        deleteAllMovies()
        deleteAllMoviesGenres()
        insertMovies(list)
    }

    @Transaction
    fun addMoviesGenres(list: List<MovieEntity>) {
        list.map {
            val movieId = it.id
            it.genreIds.map { id ->
                try {
                    addGenreToMovie(MovieGenreEntity(movieId, id))
                } catch (e: Exception) {

                }

            }
        }
    }
}