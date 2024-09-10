package com.prac.githubrepo.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.StarStateMediator
import com.prac.githubrepo.main.work.StarWorkManager
import com.prac.githubrepo.main.work.UnStarWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val starStateMediator: StarStateMediator,
    private val starWorkManager: StarWorkManager,
    private val unStarWorkManager: UnStarWorkManager
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
    val uiState: Flow<UiState> =
        combine(
            _uiState.asStateFlow(),
            starStateMediator.starStates
        ) { uiState, starStates ->
            when (uiState) {
                is UiState.ShowRepository -> {
                    val starState = starStates.find { it.id == uiState.repository.id }

                    if (starState == null) {
                        return@combine UiState.Error("알 수 없는 에러 발생")
                    }

                    uiState.copy(
                        repository = uiState.repository.copy(
                            stargazersCount = starState.stargazersCount,
                            isStarred = starState.isStarred
                        )
                    )
                }

                else -> {
                    uiState
                }
            }
        }

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

    fun starRepository(repoDetailEntity: RepoDetailEntity) {
        starStateMediator.updateStarState(
            id = repoDetailEntity.id,
            isStarred = true,
            stargazersCount = repoDetailEntity.stargazersCount + 1
        )

        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.starRepository(repoDetailEntity.owner.login, repoDetailEntity.name)
                .onSuccess {
                    starWorkManager.cancelStarWorker(repoDetailEntity.id.toString())
                }.onFailure {
                    when (it) {
                        is IOException -> {
                            starWorkManager.enqueueStarWorker(
                                repoDetailEntity.id.toString(),
                                repoDetailEntity.owner.login,
                                repoDetailEntity.name
                            )
                        }
                        else -> {
                            // 레포지토리가 사라진 경우, 레파지토리 및 사용자 명이 바뀐 경우
                            // TODO UiState to Error
                        }
                    }
                }
        }
    }

    fun unStarRepository(repoDetailEntity: RepoDetailEntity) {
        starStateMediator.updateStarState(
            id = repoDetailEntity.id,
            isStarred = false,
            stargazersCount = repoDetailEntity.stargazersCount - 1
        )

        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.unStarRepository(repoDetailEntity.owner.login, repoDetailEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {
                            unStarWorkManager.enqueueUnStarWorker(
                                repoDetailEntity.id.toString(),
                                repoDetailEntity.owner.login,
                                repoDetailEntity.name
                            )
                        }
                        else -> {
                            // 레포지토리가 사라진 경우, 레파지토리 및 사용자 명이 바뀐 경우
                            // TODO UiState to Error
                        }
                    }
                }
        }
    }
}