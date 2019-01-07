package com.billboard.movies.data.repository.mapper

import com.billboard.movies.data.local.entity.*
import com.billboard.movies.data.remote.model.*

class LocalMapper {

    fun toEntity(video: VideoDto) =
        VideoEntity(video.id, video.key, video.type, video.site, video.name, video.size)

    fun toEntity(movie: MovieDto) =
        MovieEntity(
            0,
            movie.id,
            movie.voteAverage,
            movie.title,
            movie.originalTitle,
            movie.overview,
            movie.genreIds,
            movie.releaseDate,
            movie.originalLanguage,
            movie.posterPath,
            movie.widePosterPath
        )

    fun toEntity(actor: ActorDto) =
        ActorEntity(actor.id, actor.name, actor.character, actor.profilePath)

    fun toEntity(actor: GenreDto) =
        GenreEntity(actor.id, actor.name)

    fun toEntity(language: LanguageDto) =
        LanguageEntity(language.format6391, language.name, language.resourceId)

    fun toEntity(detail: MovieDetailDto) =
        MovieDetailEntity(detail.homepage, detail.status, detail.runtime, detail.budget)
}

