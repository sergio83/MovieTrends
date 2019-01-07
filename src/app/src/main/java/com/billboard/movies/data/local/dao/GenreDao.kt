package com.billboard.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.billboard.movies.data.local.entity.GenreEntity

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(vararg genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genres: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreEntity>)

    @Update
    fun updateGenre(vararg genre: GenreEntity)

    @Delete
    fun deleteGenre(vararg genre: GenreEntity)

    @Query("DELETE FROM genres")
    fun delete()

    @Query("SELECT * FROM genres")
    fun loadAllGenres(): LiveData<List<GenreEntity>>

    @Query("SELECT * FROM genres WHERE id = :genreId")
    fun getGenre(genreId: Int): LiveData<GenreEntity>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM genres INNER JOIN movies_genres ON genres.id=movies_genres.genreId WHERE movies_genres.movieId=:movieId")
    fun getGenresOfMovie(movieId: Int): LiveData<List<GenreEntity>>

}