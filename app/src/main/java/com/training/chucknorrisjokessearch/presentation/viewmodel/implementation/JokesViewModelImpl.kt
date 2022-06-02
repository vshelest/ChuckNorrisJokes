package com.training.chucknorrisjokessearch.presentation.viewmodel.implementation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.training.chucknorrisjokessearch.domain.entity.Joke
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase
import com.training.chucknorrisjokessearch.domain.usecase.implementation.JokesUseCaseImpl
import com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction.JokesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
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
        searchStringFlow.debounce(100)
            .mapLatest {
                useCase.getJokes(it)
            }.onEach {
                _jokesFlow.value = it
            }.catch {
                Log.d("SearchString", "Error: $this")
            }.launchIn(viewModelScope)
    }
}