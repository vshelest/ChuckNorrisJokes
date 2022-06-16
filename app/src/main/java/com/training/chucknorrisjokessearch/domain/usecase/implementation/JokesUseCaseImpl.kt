package com.training.chucknorrisjokessearch.domain.usecase.implementation

import android.util.Log
import com.training.chucknorrisjokessearch.application.exception.RequestFailure
import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase
import com.training.chucknorrisjokessearch.data.network.api.ChuckNorrisAPI
import com.training.chucknorrisjokessearch.data.network.model.ModelResult
import com.training.chucknorrisjokessearch.domain.model.Joke
import com.training.chucknorrisjokessearch.domain.operationresult.GetJokesResultOrFailure
import com.training.chucknorrisjokessearch.domain.repository.abstraction.JokesRepository
import com.training.chucknorrisjokessearch.domain.repository.implementation.JokesRepositoryImpl
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.lang.Exception

class JokesUseCaseImpl(override val database: JokeDatabase) : JokesUseCase {
    private val api: ChuckNorrisAPI = ChuckNorrisAPI.create()
    private val repository: JokesRepository = JokesRepositoryImpl(database)

    override suspend fun getJokes(searchString: String): GetJokesResultOrFailure {
        Log.d("Loading Jokes", "getJokes(\"$searchString\")")
        val jokesFromDatabase = loadJokesFromDatabase(searchString)
        return if (jokesFromDatabase.isNotEmpty()) {
            Log.d(
                "Loading Jokes",
                "fromDatabase is not empty and has ${jokesFromDatabase.size} jokes"
            )
            GetJokesResultOrFailure(jokesFromDatabase)
        } else {
            Log.d("Loading Jokes", "fromDatabase empty")
            loadJokesFromServer(searchString)
        }
    }

    private suspend fun loadJokesFromDatabase(searchString: String): List<Joke> {
        Log.d("Loading Jokes", "loadJokesFromDatabase(\"$searchString\")")
        return repository.findJokesByText(searchString)
    }

    private suspend fun loadJokesFromServer(searchString: String): GetJokesResultOrFailure {
        Log.d("Loading Jokes", "loadJokesFromServer(\"$searchString\")")
        return if (searchString.length > 2) {
            try {
                val jokes = api.jokesSearch(query = searchString).result
                if (jokes.isNotEmpty()) {
                    Log.d("Loading Jokes", "fromServer is not empty and has ${jokes.size} jokes")
                    val convertedJokes = convertJokes(jokes)
                    repository.updateRepository(convertedJokes)
                    GetJokesResultOrFailure(jokesList = convertedJokes)
                } else {
                    Log.d("Loading Jokes", "fromServer is empty")
                    GetJokesResultOrFailure(jokesList = repository.jokesFlow.value)
                }
            } catch (exception: Exception) {
                Log.d("Loading Jokes", "fromServer failed with exception: ${exception.stackTrace}")
                exception.printStackTrace()
                val failure = if (exception is HttpException) {
                    RequestFailure.NetworkFailure
                } else {
                    RequestFailure.IncorrectParamsFailure
                }
                GetJokesResultOrFailure(
                    requestFailure = failure,
                    jokesList = repository.jokesFlow.value
                )
            }
        } else {
            Log.d("Loading Jokes", "fromServer didn't request, searchString is too short")
            GetJokesResultOrFailure(
                requestFailure = RequestFailure.IncorrectParamsFailure,
                jokesList = repository.jokesFlow.value
            )
        }
    }

    override suspend fun deleteJoke(joke: Joke): Flow<Unit> {
        return repository.deleteJoke(joke)
    }

    private fun convertJokes(modelResultList: List<ModelResult>): List<Joke> {
        return modelResultList.map {
            Joke(it.id, it.icon_url, it.value)
        }
    }
}