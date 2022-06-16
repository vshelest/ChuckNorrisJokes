package com.training.chucknorrisjokessearch.domain.repository.implementation

import com.training.chucknorrisjokessearch.data.database.converter.JokeConverter
import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase
import com.training.chucknorrisjokessearch.domain.model.Joke
import com.training.chucknorrisjokessearch.domain.repository.abstraction.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class JokesRepositoryImpl(override val database: JokeDatabase) : JokesRepository {
    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    override val jokesFlow: StateFlow<List<Joke>> = _jokesFlow.asStateFlow()

    private val converter: JokeConverter by lazy { JokeConverter() }

    init {
        restoreJokes()
    }

    override fun updateRepository(jokes: List<Joke>) {
        CoroutineScope(Dispatchers.Default)
            .launch {
                _jokesFlow.emit(jokes)
                saveJokes()
            }
    }

    override fun saveJokes() {
        CoroutineScope(Dispatchers.Default)
            .launch {
                val jokeEntities = converter.convert(jokesFlow.value)
                database.jokesDao().insertReplaceJokes(jokeEntities)
            }
    }

    override fun restoreJokes() {
        CoroutineScope(Dispatchers.Default)
            .launch {
                val jokeEntities = database.jokesDao().readJokes()
                val jokes = converter.convert(jokeEntities)
                _jokesFlow.emit(jokes)
            }
    }

    override suspend fun findJokesByText(searchString: String): List<Joke> {
        return converter.convert(database.jokesDao().findJokesByName(searchString))
    }

    override suspend fun deleteJoke(joke: Joke): Flow<Unit> {
        return flow {
            database.jokesDao().deleteJoke(converter.convert(joke))
        }
    }
}