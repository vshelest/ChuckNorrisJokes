package com.training.chucknorrisjokessearch.domain.usecase.implementation

import com.training.chucknorrisjokessearch.data.network.api.ChuckNorrisAPI
import com.training.chucknorrisjokessearch.data.network.model.ModelResult
import com.training.chucknorrisjokessearch.domain.entity.Joke
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase

class JokesUseCaseImpl : JokesUseCase {
    private val api: ChuckNorrisAPI = ChuckNorrisAPI.create()

    override suspend fun getJokes(searchString: String): List<Joke> {
        return if (searchString.length > 2) {
            convertJokes(api.jokesSearch(query = searchString).result)
        } else {
            emptyList()
        }
    }

    private fun convertJokes(modelResultList: List<ModelResult>): List<Joke> {
        return modelResultList.map {
            Joke(it.icon_url, it.value)
        }
    }
}