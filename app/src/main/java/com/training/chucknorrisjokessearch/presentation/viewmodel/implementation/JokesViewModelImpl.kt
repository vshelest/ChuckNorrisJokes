package com.training.chucknorrisjokessearch.presentation.viewmodel.implementation

import android.util.Log
import com.training.chucknorrisjokessearch.domain.entity.Joke
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase
import com.training.chucknorrisjokessearch.domain.usecase.implementation.JokesUseCaseImpl
import com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction.JokesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class JokesViewModelImpl : JokesViewModel() {
    private val _jokesFlow: MutableStateFlow<List<Joke>> = MutableStateFlow(emptyList())
    private val _searchStringFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val jokesFlow: StateFlow<List<Joke>> = _jokesFlow.asStateFlow()
    override val searchStringFlow: StateFlow<String> = _searchStringFlow.asStateFlow()

    private val useCase: JokesUseCase = JokesUseCaseImpl()

    override fun setNewSearchString(string: String) {
        _searchStringFlow.update {
            string
        }
    }

    init {
        searchStringFlow.onEach {
            Log.d("SearchString", "New: $it")
            useCase.getJokes(it)
        }.catch {
            Log.d("SearchString", "Error: $this")
        }.launchIn(CoroutineScope(Dispatchers.IO))

        useCase.jokesFlow.onEach {
            _jokesFlow.value = it
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}