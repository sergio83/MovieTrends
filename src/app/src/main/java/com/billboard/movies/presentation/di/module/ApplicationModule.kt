package com.billboard.movies.presentation.di.module

import android.app.Application
import android.content.Context
import com.billboard.movies.presentation.di.base.ApplicationContext
import com.billboard.movies.presentation.utils.AppApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    //Imp: agregar la anotation @ApplicationContext donde se inyecta
    @Provides
    @ApplicationContext
    internal fun provideContext(appApplication: AppApplication): Context {
        return appApplication.applicationContext
    }

    @Provides
    internal fun provideApplication(appApplication: AppApplication): Application {
        return appApplication
    }
}