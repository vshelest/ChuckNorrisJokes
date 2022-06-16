package com.training.chucknorrisjokessearch.domain.repository.abstraction

import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase
import com.training.chucknorrisjokessearch.domain.model.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface JokesRepository {
    val database: JokeDatabase
    val jokesFlow: StateFlow<List<Joke>>

    fun updateRepository(jokes: List<Joke>)
    fun saveJokes()
    fun restoreJokes()
    suspend fun findJokesByText(searchString: String): List<Joke>
    suspend fun deleteJoke(joke: Joke): Flow<Unit>
}