package com.prac.githubrepo.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.exception.GitHubApiException
import com.prac.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _isLoggedIn = MutableSharedFlow<Boolean>(replay = 1)
    val isLoggedIn = _isLoggedIn.asSharedFlow()

    fun loginWithGitHub(code: String) {
        viewModelScope.launch {
            _uiState.update { LoginUIState.Loading }

            tokenRepository.getTokenApi(code = code)
                .onSuccess {
                    _uiState.update { LoginUIState.Success }
                }.onFailure { throwable ->
                    when (throwable) {
                        is GitHubApiException.NetworkException, is GitHubApiException.UnAuthorizedException -> {
                            _uiState.update { LoginUIState.Error(throwable.message ?: "로그인을 실패했습니다.") }
                        }
                        else -> {
                            _uiState.update { LoginUIState.Error("알 수 없는 에러가 발생했습니다.") }
                        }
                    }
                }
        }
    }

    fun isLoggedIn() {
        viewModelScope.launch {
            _isLoggedIn.emit(tokenRepository.isLoggedIn())
        }
    }

}