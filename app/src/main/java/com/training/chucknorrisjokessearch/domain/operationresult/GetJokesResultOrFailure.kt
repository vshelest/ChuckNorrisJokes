package com.training.chucknorrisjokessearch.domain.operationresult

import com.training.chucknorrisjokessearch.application.exception.RequestFailure
import com.training.chucknorrisjokessearch.domain.model.Joke

data class GetJokesResultOrFailure(
    val jokesList: List<Joke>? = null,
    val requestFailure: RequestFailure? = null
)