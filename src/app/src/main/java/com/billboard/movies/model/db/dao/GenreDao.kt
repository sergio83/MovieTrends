package com.billboard.movies.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.billboard.movies.model.db.entity.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(vararg genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genres: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<Genre>)

    @Update
    fun updateGenre(vararg genre: Genre)

    @Delete
    fun deleteGenre(vararg genre: Genre)

    @Query("DELETE FROM genres")
    fun delete()

    @Query("SELECT * FROM genres")
    fun loadAllGenres(): LiveData<List<Genre>>

    @Query("SELECT * FROM genres WHERE id = :genreId")
    fun getGenre(genreId: Int): LiveData<Genre>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM genres INNER JOIN movies_genres ON genres.id=movies_genres.genreId WHERE movies_genres.movieId=:movieId")
    fun getGenresOfMovie(movieId: Int): LiveData<List<Genre>>

}