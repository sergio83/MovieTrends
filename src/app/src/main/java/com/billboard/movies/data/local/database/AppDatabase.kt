package com.billboard.movies.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.billboard.movies.data.local.converter.Converters
import com.billboard.movies.data.local.dao.GenreDao
import com.billboard.movies.data.local.dao.MoviesDao
import com.billboard.movies.data.local.entity.GenreEntity
import com.billboard.movies.data.local.entity.MovieEntity
import com.billboard.movies.data.local.entity.MovieGenreEntity


@Database(
    entities = [
        MovieEntity::class, GenreEntity::class, MovieGenreEntity::class],
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