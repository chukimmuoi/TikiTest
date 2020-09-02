package com.chukimmuoi.mbase.api

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 3/9/20.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        NEXT,
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> next(data: T): Resource<T> {
            // Timber.e("onNext: $data")
            return Resource(
                Status.NEXT,
                data,
                null
            )
        }

        fun <T> success(data: T? = null): Resource<T> {
            // Timber.e("onSuccess: $data")
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String? = "ERROR", data: T? = null): Resource<T> {
            // Timber.e("onError: $message")
            return Resource(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            // Timber.e("onLoading: $data")
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}