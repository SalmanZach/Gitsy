package com.salman.gitsy.domain.remote


/**
 * Created by Salman Saifi on 17/04/22.
 * Email - zach.salmansaifi@gmail.com
 */

sealed class Envelope<T> {
    class Loading<T> : Envelope<T>()

    data class Success<T>(val data: T) : Envelope<T>()

    data class Error<T>(val message: String) : Envelope<T>()

    companion object {

        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) =
            Success(data)

        fun <T> error(message: String) =
            Error<T>(message)
    }
}
