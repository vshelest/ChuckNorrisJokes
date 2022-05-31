package com.training.chucknorrisjokessearch.domain.usecase.abstraction

import com.training.chucknorrisjokessearch.domain.entity.Joke
import kotlinx.coroutines.flow.StateFlow

interface JokesUseCase {
    val jokesFlow: StateFlow<List<Joke>>

    fun getJokes(searchString: String)
}