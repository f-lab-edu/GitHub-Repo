package com.prac.githubrepo.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {
    sealed class UiState {
        data object Idle : UiState()

        data object Loading : UiState()

        data class ShowRepository(
            val repository : RepoDetailEntity
        ) : UiState()

        data class Error(
            val errorMessage: String
        ) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getRepository(userName: String?, repoName: String?) {
        if (_uiState.value != UiState.Idle) return

        _uiState.update { UiState.Loading }

        if (userName == null || repoName == null) {
            _uiState.update { UiState.Error("잘못된 접근입니다.") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.getRepository(userName, repoName)
                .onSuccess { repoDetailEntity ->
                    _uiState.update {
                        UiState.ShowRepository(repoDetailEntity)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        UiState.Error(throwable.message.toString())
                    }
                }
        }
    }
}