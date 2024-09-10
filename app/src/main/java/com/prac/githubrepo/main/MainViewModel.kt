package com.prac.githubrepo.main

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.prac.data.entity.RepoEntity
import com.prac.data.exception.GitHubApiException
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.work.StarWorkManager
import com.prac.githubrepo.main.work.UnStarWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val starStateMediator: StarStateMediator,
    private val starWorkManager: StarWorkManager,
    private val unStarWorkManager: UnStarWorkManager
): ViewModel() {
    sealed class UiState {
        data object Idle : UiState()

        data class ShowPagingData(
            val repositories : PagingData<RepoEntity>,
            val loadState: LoadState? = null
        ) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private fun getRepositories() {
        viewModelScope.launch {
            if (_uiState.value != UiState.Idle) return@launch

            combine(
                repoRepository.getRepositories().cachedIn(viewModelScope),
                starStateMediator.starStates
            ) { pagingData, starStates ->
                starStates.fold(pagingData) { acc, item ->
                    acc.map { repoEntity ->
                        if (repoEntity.id == item.id) repoEntity.copy(isStarred = item.isStarred, stargazersCount = item.stargazersCount)
                        else repoEntity
                    }
                }
            }.collect { transformedPagingData ->
                _uiState.update { UiState.ShowPagingData(transformedPagingData) }
            }

        }
    }

    fun updateLoadState(loadState: LoadState) {
        if (_uiState.value !is UiState.ShowPagingData) return

        _uiState.update {
            (it as UiState.ShowPagingData).copy(loadState = loadState)
        }
    }

    fun starRepository(repoEntity: RepoEntity) {
        starStateMediator.updateStarState(
            id = repoEntity.id,
            isStarred = true,
            stargazersCount = repoEntity.stargazersCount + 1
        )

        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.starRepository(repoEntity.owner.login, repoEntity.name)
                .onSuccess {
                    starWorkManager.cancelStarWorker(repoEntity.id.toString())
                }.onFailure {
                    when (it) {
                        is IOException -> {
                            starWorkManager.enqueueStarWorker(
                                repoEntity.id.toString(),
                                repoEntity.owner.login,
                                repoEntity.name
                            )
                        }
                        else -> {
                            // 레포지토리가 사라진 경우, 레파지토리 및 사용자 명이 바뀐 경우
                            // TODO show alert dialog and Refresh PagingData
                        }
                    }
                }
        }
    }

    fun unStarRepository(repoEntity: RepoEntity) {
        starStateMediator.updateStarState(
            id = repoEntity.id,
            isStarred = false,
            stargazersCount = repoEntity.stargazersCount - 1
        )

        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.unStarRepository(repoEntity.owner.login, repoEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {
                            unStarWorkManager.enqueueUnStarWorker(
                                repoEntity.id.toString(),
                                repoEntity.owner.login,
                                repoEntity.name
                            )
                        }
                        else -> {
                            // 레포지토리가 사라진 경우, 레파지토리 및 사용자 명이 바뀐 경우
                            // TODO show alert dialog and Refresh PagingData
                        }
                    }
                }
        }
    }

    init {
        getRepositories()
    }
}