package com.billboard.movies.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class Genre(@PrimaryKey val id: Int, val name: String)