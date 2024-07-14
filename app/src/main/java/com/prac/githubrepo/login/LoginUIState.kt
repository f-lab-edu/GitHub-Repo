package com.prac.githubrepo.login

data class LoginUIState(
    val accessToken: String? = null,
    val isLoading: Boolean = false,
    val loginException: Exception? = null
)