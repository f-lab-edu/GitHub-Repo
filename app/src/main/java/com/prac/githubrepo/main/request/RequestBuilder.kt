package com.prac.githubrepo.main.request

import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.UiStateUpdater
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope

class RequestBuilder @AssistedInject constructor(
    private val repoRepository: RepoRepository,
    @Assisted private val uiStateUpdater: UiStateUpdater,
    @Assisted private val lifecycleScope: CoroutineScope
) {
    @AssistedFactory
    interface Factory {
        fun create(
            uiStateUpdater: UiStateUpdater,
            lifecycleScope: CoroutineScope
        ): RequestBuilder
    }
}