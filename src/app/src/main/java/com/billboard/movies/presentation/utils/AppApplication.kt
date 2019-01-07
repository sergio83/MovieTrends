package com.billboard.movies.presentation.utils

import android.app.Activity
import android.app.Application
import com.billboard.movies.BuildConfig
import com.billboard.movies.presentation.di.base.AppInjector
import com.billboard.movies.data.local.sharedPreference.AppPreferences
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class AppApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }

        AppInjector.init(this)
        Fresco.initialize(this)
        AppPreferences.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}
