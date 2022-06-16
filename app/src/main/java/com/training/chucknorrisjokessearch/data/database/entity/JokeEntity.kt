package com.training.chucknorrisjokessearch.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke")
data class JokeEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "text") val text: String
)