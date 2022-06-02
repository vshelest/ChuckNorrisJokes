package com.training.chucknorrisjokessearch.domain.usecase.abstraction

import com.training.chucknorrisjokessearch.domain.entity.Joke
import kotlinx.coroutines.flow.StateFlow

interface JokesUseCase {
    suspend fun getJokes(searchString: String): List<Joke>
}