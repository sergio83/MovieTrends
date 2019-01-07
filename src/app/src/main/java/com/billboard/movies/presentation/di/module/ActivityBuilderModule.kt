package com.billboard.movies.presentation.di.module

import com.billboard.movies.presentation.ui.billboard.BillboardActivity
import com.billboard.movies.presentation.ui.movieDetail.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilderModule {

    //Generates an AndroidInjector for the return type of this method
    // Crea un Injector para el tipo que se retorna, lo que va hacer es injectar
    // Las dependencias a la activity

    @ContributesAndroidInjector
    abstract fun contributeBillboardActivity(): BillboardActivity

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity

}
