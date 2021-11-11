package com.codeex.domain.model

/**
 * State Management for UI & Data.
 */
sealed class State<out T : Any>
data class Success<out T : Any>(val data: T) : State<T>()
data class Failure(val httpError: HttpError) : State<Nothing>()

class HttpError(val throwable: Throwable, val errorCode: Int = 0)

inline fun <T : Any> State<T>.onSuccess(action: (T) -> Unit): State<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T : Any> State<T>.onFailure(action: (HttpError) -> Unit) {
    if (this is Failure) action(httpError)
}