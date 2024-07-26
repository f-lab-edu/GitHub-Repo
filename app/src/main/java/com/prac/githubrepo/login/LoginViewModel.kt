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
    sealed class UiState {
        data object Idle : UiState()

        data object Loading : UiState()

        data class Error(
            val errorMessage : String
        ) : UiState()
    }

    sealed class Event {
        data object Success : Event()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        checkAutoLogin()
    }

    fun setUiStateToIdle() {
        _uiState.update { UiState.Idle }
    }

    fun loginWithGitHub(code: String) {
        viewModelScope.launch {
            if (_uiState.value != UiState.Idle) return@launch

            _uiState.update { UiState.Loading }

            tokenRepository.getTokenApi(code = code)
                .onSuccess {
                    _event.emit(Event.Success)
                }.onFailure { throwable ->
                    when (throwable) {
                        is GitHubApiException.NetworkException, is GitHubApiException.UnAuthorizedException -> {
                            _uiState.update { UiState.Error(throwable.message ?: "로그인을 실패했습니다.") }
                        }
                        else -> {
                            _uiState.update { UiState.Error("알 수 없는 에러가 발생했습니다.") }
                        }
                    }
                }
        }
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            if (_uiState.value != UiState.Idle) return@launch

            _uiState.update { UiState.Loading }

            if (tokenRepository.isLoggedIn()) _event.emit(Event.Success)
            else _uiState.update { UiState.Idle }
        }
    }

}