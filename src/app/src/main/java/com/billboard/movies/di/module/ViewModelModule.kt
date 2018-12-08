package com.billboard.movies.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billboard.movies.di.base.ViewModelKey
import com.billboard.movies.viewmodel.AppViewModelFactory
import com.billboard.movies.viewmodel.MovieDetailViewModel
import com.billboard.movies.viewmodel.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    //@Bind es simil a provider, devuelve la interfaz y recibe una implementación de la misma (esto obliga a que la clase sea abstracta).

    //Injecta este objeto en un Map usando ViewModelKey como key y el Provider como value.
    // El provider va a crear el objeto

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
