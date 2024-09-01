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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val starStateMediator: StarStateMediator
): ViewModel() {
    sealed class UiState {
        data object Idle : UiState()

        data object Loading : UiState()

        data class ShowPagingData(
            val repositories : PagingData<RepoEntity>
        ) : UiState()
    }

    init {
        getRepositories()
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
}