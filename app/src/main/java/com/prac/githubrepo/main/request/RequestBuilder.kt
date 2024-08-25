package com.prac.githubrepo.main.request

import android.view.View
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

    private var view: View? = null

    fun setView(view: View) : RequestBuilder = apply {
        this.view = view
    }
}