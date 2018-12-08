package com.billboard.movies.di.component

import com.billboard.movies.di.module.ActivityBuilderModule
import com.billboard.movies.di.module.AppModule
import com.billboard.movies.di.module.ApplicationModule
import com.billboard.movies.utils.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/*
 * Contiene los módulos con las dependencias necesarias, así como las clases donde estas se necesitan.
 * hace de enlace los @Modules y los @Inject.
 */

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ApplicationModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AppApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppApplication)
}


