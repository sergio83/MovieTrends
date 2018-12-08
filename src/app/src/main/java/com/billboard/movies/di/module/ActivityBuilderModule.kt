package com.billboard.movies.di.module

import com.billboard.movies.ui.activity.BillboardActivity
import com.billboard.movies.ui.activity.MovieDetailActivity
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
