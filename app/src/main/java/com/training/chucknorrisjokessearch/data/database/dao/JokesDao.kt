package com.training.chucknorrisjokessearch.data.database.dao

import androidx.room.*
import com.training.chucknorrisjokessearch.data.database.entity.JokeEntity

@Dao
interface JokesDao {
    @Query("SELECT * FROM joke")
    fun readJokes(): List<JokeEntity>

    @Query("SELECT * FROM joke WHERE joke.text >= :searchString")
    fun findJokesByName(searchString: String): List<JokeEntity>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIgnoreJokes(joke: List<JokeEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateReplaceJokes(joke: List<JokeEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreJokes(joke: List<JokeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplaceJokes(joke: List<JokeEntity>)

    @Delete
    fun deleteJoke(joke: JokeEntity)
}