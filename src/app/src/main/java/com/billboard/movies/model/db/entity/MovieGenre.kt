package com.billboard.movies.model.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

/*
Para poder insertar una relacion primero deben estar insertadas los objetos en cuestion.
onDelete = CASCADE Es importante ponerlo para que se borre la relacion cuando se borra una Movie, caso contrario crashea
* */
@Entity(
    tableName = "movies_genres",
    indices = arrayOf(Index(value = ["movieId", "genreId"], unique = true)),
    primaryKeys = arrayOf("movieId", "genreId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = Movie::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movieId"),onDelete = CASCADE),
        ForeignKey(entity = Genre::class, parentColumns = arrayOf("id"), childColumns = arrayOf("genreId"),onDelete = CASCADE)
    )
)
data class MovieGenre(val movieId: Int, val genreId: Int)