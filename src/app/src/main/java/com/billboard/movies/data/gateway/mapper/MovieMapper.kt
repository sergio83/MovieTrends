package com.billboard.movies.data.gateway.mapper

import com.billboard.movies.data.local.entity.*
import com.billboard.movies.domain.model.*

class MovieMapper {
    fun toModel(video: VideoEntity) =
        Video(video.id, video.key, video.type, video.site, video.name, video.size)

    fun toModel(movie: MovieEntity) =
        Movie(
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

    fun toModel(actor: ActorEntity) =
        Actor(actor.id, actor.name, actor.character, actor.profilePath)

    fun toModel(actor: GenreEntity) =
        Genre(actor.id, actor.name)

    fun toModel(detail: MovieDetailEntity) =
        MovieDetail(detail.homepage, detail.status, detail.runtime, detail.budget)
}
