package com.prac.githubrepo.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.backoff.BackOffWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val backOffWorkManager: BackOffWorkManager
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

            repoRepository.getRepositories().cachedIn(viewModelScope).collect { pagingData ->
                _uiState.update { UiState.ShowPagingData(pagingData) }
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
        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.starLocalRepository(repoEntity.id, repoEntity.stargazersCount + 1)

            repoRepository.starRepository(repoEntity.owner.login, repoEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {

                        }
                        else -> {
                            // TODO Show Error Message
                        }
                    }
                }
        }
    }

    fun unStarRepository(repoEntity: RepoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.unStarLocalRepository(repoEntity.id, repoEntity.stargazersCount - 1)

            repoRepository.unStarRepository(repoEntity.owner.login, repoEntity.name)
                .onFailure {
                    when (it) {
                        is IOException -> {

                        }
                        else -> {
                            // TODO Show Error Message
                        }
                    }
                }
        }
    }

    init {
        getRepositories()
    }
}