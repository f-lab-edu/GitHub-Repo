package com.prac.githubrepo.main.request

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class StarRequest internal constructor(
    private val repoRepository: RepoRepository,
    private val repoEntity: RepoEntity,
    private val scope: CoroutineScope,
) : Request {
    override fun checkStarredState() {

    }

    override fun cancel() {

    }

    override fun isCompleted() : Boolean {
        // TODO Not yet implemented
        return false
    }
}