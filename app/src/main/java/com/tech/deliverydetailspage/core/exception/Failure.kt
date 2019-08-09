package com.tech.deliverydetailspage.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    class GenericError : Failure()
    class UnauthorizedError : Failure()
    class RequestError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
