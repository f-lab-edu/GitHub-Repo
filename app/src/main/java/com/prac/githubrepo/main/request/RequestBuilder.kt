package com.prac.githubrepo.main.request

import com.prac.data.repository.RepoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope

class RequestBuilder @AssistedInject constructor(
    private val repoRepository: RepoRepository,
    @Assisted private val scope: CoroutineScope
) {
    @AssistedFactory
    interface Factory {
        fun create(scope: CoroutineScope): RequestBuilder
    }
}