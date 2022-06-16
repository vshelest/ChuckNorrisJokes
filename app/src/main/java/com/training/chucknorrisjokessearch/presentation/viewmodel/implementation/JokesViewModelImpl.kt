package com.training.chucknorrisjokessearch.presentation.viewmodel.implementation

import android.util.Log
import androidx.room.RoomDatabase
import com.training.chucknorrisjokessearch.application.exception.RequestFailure
import com.training.chucknorrisjokessearch.data.database.db.JokeDatabase
import com.training.chucknorrisjokessearch.domain.model.Joke
import com.training.chucknorrisjokessearch.domain.model.JokeAction
import com.training.chucknorrisjokessearch.domain.usecase.abstraction.JokesUseCase
import com.training.chucknorrisjokessearch.domain.usecase.implementation.JokesUseCaseImpl
import com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction.JokesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class JokesViewModelImpl : JokesViewModel() {
    private val _jokesFlow: MutableStateFlow<List<Joke>> = MutableStateFlow(emptyList())
    private val _searchStringFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val _errorFlow: MutableSharedFlow<RequestFailure> = MutableSharedFlow()

    override val jokesFlow: StateFlow<List<Joke>> = _jokesFlow.asStateFlow()
    override val searchStringFlow: StateFlow<String> = _searchStringFlow.asStateFlow()
    override val errorFlow: SharedFlow<RequestFailure> = _errorFlow.asSharedFlow()

    private lateinit var useCase: JokesUseCase

    override fun setNewSearchString(string: String) {
        _searchStringFlow.update {
            string
        }
    }

    override fun setDatabase(database: RoomDatabase) {
        useCase = JokesUseCaseImpl(
            database = database as JokeDatabase
        )
    }

    init {
        searchStringFlow.debounce(100)
            .mapLatest {
                if (::useCase.isInitialized) {
                    try {
                        val result = useCase.getJokes(it)
                        if (result.requestFailure != null) {
                            _errorFlow.emit(result.requestFailure)
                            result.jokesList ?: emptyList()
                        } else {
                            result.jokesList ?: emptyList()
                        }
                    } catch (e: Exception) {
                        val exception = e
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }.onEach {
                _jokesFlow.value = it
            }.catch {
                Log.d("SearchString", "Error: $this")
            }.launchIn(CoroutineScope(Dispatchers.Default))
    }

    suspend fun handleJokeAction(action: JokeAction) {
        when (action) {
            is JokeAction.DeleteJoke -> useCase.deleteJoke(action.joke)
        }
    }
}