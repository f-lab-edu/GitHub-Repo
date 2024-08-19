package com.prac.githubrepo.main.request

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.UiStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class RepositoryStarRequest(
    private val repoRepository: RepoRepository,
    private val repoEntity: RepoEntity,
    private val lifecycleScope: CoroutineScope,
    private val uiStateUpdater: UiStateUpdater
) : Request {
    private var job: Job? = null

    override fun begin() {

    }

    override fun cancel() {

    }

    override fun isComplete(): Boolean {
        // TODO
        return false
    }
}