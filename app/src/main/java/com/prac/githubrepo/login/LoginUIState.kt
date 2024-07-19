package com.prac.githubrepo.login

sealed class LoginUIState {
    data object Idle : LoginUIState()

    data object Loading : LoginUIState()

    data object Success : LoginUIState()

    data class Error(
        val errorMessage : String
    ) : LoginUIState()
}