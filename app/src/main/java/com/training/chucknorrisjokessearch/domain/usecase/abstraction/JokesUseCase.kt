package com.training.chucknorrisjokessearch.domain.usecase.abstraction

import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase
import com.training.chucknorrisjokessearch.domain.model.Joke
import com.training.chucknorrisjokessearch.domain.operationresult.GetJokesResultOrFailure
import kotlinx.coroutines.flow.Flow

interface JokesUseCase {
    val database: JokeDatabase
    suspend fun getJokes(searchString: String): GetJokesResultOrFailure
    suspend fun deleteJoke(joke: Joke): Flow<Unit>
}