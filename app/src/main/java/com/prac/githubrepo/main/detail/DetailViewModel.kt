package com.prac.githubrepo.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.backoff.BackOffWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val backOffWorkManager: BackOffWorkManager
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
    val uiState: Flow<UiState> = _uiState.asStateFlow()

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
                    repoRepository.getStarStateAndStarCount(repoDetailEntity.id).collect { pair ->
                        val isStarred = pair.first
                        val stargazersCount = pair.second

                        // Room 에서 repoDetailEntity.id 값이 없을 경우에 null 을 반환한다.
                        if (stargazersCount == null) {
                            _uiState.update { UiState.Error("존재하지 않는 레파지토리입니다.") }
                            return@collect
                        }

                        // List 화면에서 Star Check 가 완료되기 전에 사용자가 Detail 화면으로 넘어온 경우 null 을 반환한다.
                        if (isStarred == null) {
                            repoRepository.isStarred(repoDetailEntity.id, repoDetailEntity.name)
                            return@collect
                        }

                        _uiState.update {
                            UiState.ShowRepository(repoDetailEntity.copy(isStarred = isStarred, stargazersCount = stargazersCount))
                        }
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        UiState.Error(throwable.message.toString())
                    }
                }
        }
    }

    fun starRepository(repoDetailEntity: RepoDetailEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.starLocalRepository(repoDetailEntity.id, repoDetailEntity.stargazersCount + 1)

            repoRepository.starRepository(repoDetailEntity.owner.login, repoDetailEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {
                            backOffWorkManager.addStarWorkWithUniqueID(repoDetailEntity.id, repoDetailEntity.owner.login, repoDetailEntity.name)
                        }
                        else -> {
                            // TODO Show Error Message
                        }
                    }
                }
        }
    }

    fun unStarRepository(repoDetailEntity: RepoDetailEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.unStarLocalRepository(repoDetailEntity.id, repoDetailEntity.stargazersCount - 1)

            repoRepository.unStarRepository(repoDetailEntity.owner.login, repoDetailEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {
                            backOffWorkManager.addUnStarWorkWithUniqueID(repoDetailEntity.id, repoDetailEntity.owner.login, repoDetailEntity.name)
                        }
                        else -> {
                            // TODO Show Error Message
                        }
                    }
                }
        }
    }
}