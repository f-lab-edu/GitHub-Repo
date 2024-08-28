package com.prac.githubrepo.main.request

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StarRequest internal constructor(
    private val repoRepository: RepoRepository,
    private val repoEntity: RepoEntity,
    private val scope: CoroutineScope,
) : Request {
    private var job: Job? = null

    override fun checkStarredState() {
        cancel()

        job = scope.launch(Dispatchers.IO) {
            repoRepository.isStarred(repoEntity.name)
                .onSuccess {
                    // TODO Add Ui Update Interface
                }.onFailure {
                    // TODO Add Ui Update Interface
                }
        }
    }

    override fun cancel() {
        job?.let {
            if (!isCompleted()) it.cancel()
            job = null
        }
    }

    override fun isCompleted() : Boolean {
        return job?.isCompleted == true
    }
}