package com.prac.githubrepo.main.request

import com.prac.data.entity.RepoEntity
import com.prac.githubrepo.main.star.StarStateFetcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class StarStateRequestImpl internal constructor(
    private val starStateFetcher: StarStateFetcher,
    private val repoEntity: RepoEntity,
    private val scope: CoroutineScope,
) : StarStateRequest {
    private var job: Job? = null

    override fun fetchStarState() {
        cancel()

        job = scope.launch(Dispatchers.IO) {
            starStateFetcher.fetchStarState(repoEntity)
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