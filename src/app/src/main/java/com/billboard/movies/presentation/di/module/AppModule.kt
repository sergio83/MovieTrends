package com.billboard.movies.presentation.di.module

import android.app.Application
import com.billboard.movies.data.local.database.AppDatabase
import com.billboard.movies.data.local.dao.GenreDao
import com.billboard.movies.data.local.dao.MoviesDao
import com.billboard.movies.data.remote.api.ApiClient
import com.billboard.movies.data.remote.api.utils.ServiceGenerator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
Module Se usa para marcar una clase como proveedora de dependencias ya que dentro contendrá métodos con
la notación @Provides de tal forma que al momento de crear una instancia de una clase y esta
requiera dependencias dagger sabrá en qué lugar ir a buscar dichos objetos.
*/
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ServiceGenerator.createService(ApiClient::class.java)
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MoviesDao {
        return db.moviesDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(db: AppDatabase): GenreDao {
        return db.genresDao()
    }
}
