package com.jacksoncheek.userprofile.common.internal.logic

/**
 * Monad for an expected/unexpected result.
 */
sealed class Result<out T> {

    data class Expected<out T>(val result: T) : Result<T>()

    data class Unexpected(val error: String) : Result<Nothing>()
}