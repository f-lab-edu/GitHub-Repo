package com.prac.githubrepo.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.exception.GitHubApiException
import com.prac.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    fun fetchAccessToken(clientID: String, clientSecret: String, code: String) {
        viewModelScope.launch {
            _uiState.value = LoginUIState(isLoading = true)

            tokenRepository.getAccessTokenApi(clientID = clientID, clientSecret = clientSecret, code = code)
                .onSuccess {
                    _accessToken.value = it.accessToken
                    _uiState.value = LoginUIState(isLoading = false)
                }.onFailure {
                    when (it) {
                        is GitHubApiException.NetworkException, is GitHubApiException.UnAuthorizedException -> {
                            _uiState.value = LoginUIState(isLoading = false, userMessage = it.message)
                        }
                        else -> {
                            _uiState.value = LoginUIState(isLoading = false, userMessage = "알 수 없는 오류가 발생했습니다.")
                        }
                    }
                }
        }
    }

}