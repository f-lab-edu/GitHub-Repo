package com.prac.githubrepo.main.request

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.UiStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepositoryStarRequest(
    private val repoRepository: RepoRepository,
    private val repoEntity: RepoEntity,
    private val lifecycleScope: CoroutineScope,
    private val uiStateUpdater: UiStateUpdater
) : Request {
    private var job: Job? = null

    override fun begin() {
        if (repoEntity.isStarred != null) return

        job = lifecycleScope.launch {
            repoRepository.isStarred(repoEntity.name)
                .onSuccess { uiStateUpdater.updateIsStarred(repoEntity.id, it) }
                .onFailure { uiStateUpdater.updateIsStarred(repoEntity.id, false) }
        }
    }

    override fun cancel() {
        job?.let {
            it.cancel()
            job = null
        }
    }

    override fun isComplete(): Boolean {
        return job?.isActive != true
    }
}