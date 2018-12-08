package com.billboard.movies.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.billboard.movies.model.db.converter.Converters
import com.billboard.movies.model.db.dao.GenreDao
import com.billboard.movies.model.db.dao.MoviesDao
import com.billboard.movies.model.db.entity.Genre
import com.billboard.movies.model.db.entity.Movie
import com.billboard.movies.model.db.entity.MovieGenre


@Database(
    entities = [
        Movie::class, Genre::class, MovieGenre::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun genresDao(): GenreDao

    companion object {


        private var sInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "basic.db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            }
            return sInstance!!
        }

        fun onDestroy() {
            sInstance = null
        }
    }
}