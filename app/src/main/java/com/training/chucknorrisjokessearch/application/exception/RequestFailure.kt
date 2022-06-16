package com.training.chucknorrisjokessearch.application.exception

sealed class RequestFailure {
    object NetworkFailure : RequestFailure()
    object IncorrectParamsFailure : RequestFailure()
}
