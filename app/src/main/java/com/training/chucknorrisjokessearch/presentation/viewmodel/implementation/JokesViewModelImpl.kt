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
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class JokesViewModelImpl : JokesViewModel() {
    private val _jokesFlow: MutableStateFlow<List<Joke>> = MutableStateFlow(emptyList())
    private val _searchStringFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val _errorFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val jokesFlow: StateFlow<List<Joke>> = _jokesFlow.asStateFlow()
    override val searchStringFlow: StateFlow<String> = _searchStringFlow.asStateFlow()
    override val errorFlow: StateFlow<String> = _errorFlow.asStateFlow()

    private val useCase: JokesUseCase = JokesUseCaseImpl()

    override fun setNewSearchString(string: String) {
        _searchStringFlow.update {
            string
        }
    }

    init {
        searchStringFlow.debounce(100)
            .mapLatest {
                try {
                    useCase.getJokes(it)
                } catch (e: HttpException) {
                    //TODO: send readable error instead
                    _errorFlow.emit(e.response()?.errorBody().toString())
                    emptyList()
                }
            }.onEach {
                _jokesFlow.value = it
            }.catch {
                Log.d("SearchString", "Error: $this")
            }.launchIn(viewModelScope)
    }
}