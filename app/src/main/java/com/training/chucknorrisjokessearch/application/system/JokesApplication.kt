package com.training.chucknorrisjokessearch.application.system

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.training.chucknorrisjokessearch.BuildConfig
import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase

class JokesApplication : Application() {

    lateinit var database: RoomDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            JokeDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    override fun onTerminate() {
        super.onTerminate()
        database.close()
    }
}