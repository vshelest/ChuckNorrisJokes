package com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction

import androidx.lifecycle.ViewModel
import com.training.chucknorrisjokessearch.domain.entity.Joke
import kotlinx.coroutines.flow.StateFlow

abstract class JokesViewModel : ViewModel() {
    abstract val jokesFlow: StateFlow<List<Joke>>
    abstract val searchStringFlow: StateFlow<String>
    abstract val errorFlow: StateFlow<String>

    abstract fun setNewSearchString(string: String)
}