package com.training.chucknorrisjokessearch.domain.model

import kotlinx.coroutines.flow.Flow

sealed class JokeAction(val joke: Joke) {
    abstract fun actionCallback(): Flow<Unit>
    abstract class DeleteJoke(joke: Joke) : JokeAction(joke)
}
