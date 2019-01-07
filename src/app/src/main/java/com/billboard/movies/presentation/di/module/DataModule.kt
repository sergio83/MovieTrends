package com.billboard.movies.presentation.di.module

import com.billboard.movies.data.gateway.LanguageGatewayImpl
import com.billboard.movies.data.gateway.MovieGatewayImpl
import com.billboard.movies.data.repository.LanguageRepository
import com.billboard.movies.data.repository.MovieRepository
import com.billboard.movies.data.repository.mapper.LocalMapper
import com.billboard.movies.domain.gateway.LanguageGateway
import com.billboard.movies.domain.gateway.MovieGateway
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideLocalMapper(): LocalMapper {
        return LocalMapper()
    }

    @Provides
    @Singleton
    internal fun provideMovieRepository(movieRepository: MovieRepository): MovieGateway {
        return MovieGatewayImpl(movieRepository)
    }

    @Provides
    @Singleton
    internal fun provideLanguageRepository(languageRepository: LanguageRepository): LanguageGateway {
        return LanguageGatewayImpl(languageRepository)
    }
}