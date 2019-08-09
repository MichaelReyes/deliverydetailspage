package com.tech.deliverydetailspage.core.service

import com.tech.deliverydetailspage.core.exception.Failure
import com.tech.deliverydetailspage.core.functional.Either
import com.tech.deliverydetailspage.core.platform.libraries.logger.AppLogger
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient
@Inject constructor() {

    fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T,
        postRequest: (R) -> Unit = {}
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> {
                    val transformed = transform((response.body() ?: default))
                    postRequest(transformed)
                    Either.Right(transformed)
                }
                false -> Either.Left(getFailureType(response.code()))
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            AppLogger.error(exception.message!!)
            Either.Left(Failure.ServerError())
        }
    }

    private fun getFailureType(httpCode: Int): Failure {
        return when (httpCode) {
            401 -> return Failure.UnauthorizedError()
            400 -> return Failure.RequestError()
            in 402..409 -> return Failure.RequestError()
            in 500..509 -> return Failure.ServerError()
            else -> Failure.GenericError()
        }
    }

}