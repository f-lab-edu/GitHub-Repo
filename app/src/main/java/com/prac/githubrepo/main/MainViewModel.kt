package com.prac.githubrepo.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.entity.RepoEntity
import com.prac.data.exception.GitHubApiException
import com.prac.data.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository
): ViewModel() {
    sealed class UiState {
        data object Idle : UiState()

        data object Loading : UiState()

        data class Success(
            val repositories : List<RepoEntity>
        ) : UiState()

        data class Error(
            val errorMessage: String
        ) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getRepositories() {
        viewModelScope.launch {
            if (_uiState.value != UiState.Idle) return@launch

            _uiState.update { UiState.Loading }

            // AccessToken 으로 유저 정보 가져오는 API 추가할 예정
            repoRepository.getRepositories("GongDoMin")
                .onSuccess { repoRepositories ->
                    _uiState.update { UiState.Success(repoRepositories) }
                }
                .onFailure { throwable ->
                    when (throwable) {
                        is GitHubApiException.NetworkException, is GitHubApiException.UnAuthorizedException -> {
                            _uiState.update { UiState.Error(throwable.message ?: "레파지토리를 불러오는데 실패했습니다.") }
                        }
                        else -> {
                            _uiState.update { UiState.Error("알 수 없는 에러가 발생했습니다.") }
                        }
                    }
                }
        }
    }
}