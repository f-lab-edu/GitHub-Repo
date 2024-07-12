package com.prac.data.exception

sealed class GitHubApiException() : Exception() {
    data class NetworkException(
        override val message: String
    ) : GitHubApiException()

    data class UnAuthorizedException(
        override val message: String
    ) : GitHubApiException()
}