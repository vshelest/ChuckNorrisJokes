package com.training.chucknorrisjokessearch.domain.usecase.implementation

import com.training.chucknorrisjokessearch.data.network.api.ChuckNorrisAPI
import com.training.chucknorrisjokessearch.data.network.model.ModelResult
import com.training.chucknorrisjokessearch.domain.entity.Joke
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JokesUseCaseImpl : JokesUseCase {
    private val api: ChuckNorrisAPI = ChuckNorrisAPI.create()
    private val _jokesFlow: MutableStateFlow<List<Joke>> = MutableStateFlow(emptyList())
    override val jokesFlow: StateFlow<List<Joke>> = _jokesFlow.asStateFlow()

    override fun getJokes(searchString: String) {
        if (searchString.length > 2) {
            CoroutineScope(IO).launch {
                _jokesFlow.emit(convertJokes(api.jokesSearch(query = searchString).result))
            }
        } else {
            _jokesFlow.value = emptyList()
        }
    }

    private fun convertJokes(modelResultList: List<ModelResult>): List<Joke> {
        return modelResultList.map {
            Joke(it.icon_url, it.value)
        }
    }
}