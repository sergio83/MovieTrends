package com.billboard.movies.di.module

import android.app.Application
import com.billboard.movies.model.db.AppDatabase
import com.billboard.movies.model.db.dao.GenreDao
import com.billboard.movies.model.db.dao.MoviesDao
import com.billboard.movies.model.rest.ApiClient
import com.billboard.movies.model.rest.base.ServiceGenerator
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
