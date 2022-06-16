package com.training.chucknorrisjokessearch.data.database.converter

import com.training.chucknorrisjokessearch.data.database.entity.JokeEntity
import com.training.chucknorrisjokessearch.domain.model.Joke

class JokeConverter {
    fun convert(joke: Joke): JokeEntity {
        return JokeEntity(
            id = joke.id,
            imageUrl = joke.imageUrl,
            text = joke.text
        )
    }

    private fun convert(jokeEntity: JokeEntity): Joke {
        return Joke(
            id = jokeEntity.id,
            imageUrl = jokeEntity.imageUrl,
            text = jokeEntity.text
        )
    }

    fun convert(jokes: List<Joke>): List<JokeEntity> {
        return jokes.map {
            convert(it)
        }
    }

    @JvmName("convert1")
    fun convert(jokeEntities: List<JokeEntity>): List<Joke> {
        return jokeEntities.map {
            convert(it)
        }
    }
}