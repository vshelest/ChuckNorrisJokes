package com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction

import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.training.chucknorrisjokessearch.application.exception.RequestFailure
import com.training.chucknorrisjokessearch.domain.model.Joke
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class JokesViewModel : ViewModel() {
    abstract val jokesFlow: StateFlow<List<Joke>>
    abstract val searchStringFlow: StateFlow<String>
    abstract val errorFlow: SharedFlow<RequestFailure>

    abstract fun setNewSearchString(string: String)
    abstract fun setDatabase(database: RoomDatabase)
}