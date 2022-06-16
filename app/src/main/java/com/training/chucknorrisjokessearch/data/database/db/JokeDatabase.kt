package com.training.chucknorrisjokessearch.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.training.chucknorrisjokessearch.data.database.dao.JokesDao
import com.training.chucknorrisjokessearch.data.database.entity.JokeEntity

@Database(entities = [JokeEntity::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}