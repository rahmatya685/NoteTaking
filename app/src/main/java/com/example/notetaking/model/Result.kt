package com.example.notetaking.model

import java.lang.Exception

/**
 * A generic class that holds a value with its loading state.
 * @param <T>
 */
sealed class Result<out I> {
    data class Success<I>(val data:I) : Result<I>()
    data class Error(val e: Exception) : Result<Nothing>()
    data class Loading(val m:String) : Result<String>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$e]"
            is Loading -> "Loading[${this.m}]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && this.data != null