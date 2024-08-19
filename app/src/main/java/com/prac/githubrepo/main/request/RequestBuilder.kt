package com.prac.githubrepo.main.request

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import com.prac.githubrepo.main.RepoStarUpdater
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
    companion object {
        private val tagID = R.string.RepoStartUpdater
    }

    @AssistedFactory
    interface Factory {
        fun create(
            uiStateUpdater: UiStateUpdater,
            lifecycleScope: CoroutineScope
        ): RequestBuilder
    }

    private var repoEntity: RepoEntity? = null
    private var view: View? = null

    fun setData(repoEntity: RepoEntity) : RequestBuilder = apply {
        this.repoEntity = repoEntity
    }

    fun setView(view: View) = apply {
        this.view = view
    }

    fun build() = apply {
        val repoEntity = checkNotNull(repoEntity) { "repoEntity is null" }
        val view = checkNotNull(view) { "view is null" }

        val request = RepositoryStarRequest(
            repoRepository = repoRepository,
            lifecycleScope = lifecycleScope,
            uiStateUpdater = uiStateUpdater,
            repoEntity = repoEntity
        )

        if (view.hasRequest()) {
            val repoStarUpdater = (view.getTag(tagID) as RepoStarUpdater)
            repoStarUpdater.addOrUpdateListener(request)
            return@apply
        }

        val repoStarUpdater = RepoStarUpdater(request, view)
        view.setTag(tagID, repoStarUpdater)
        repoStarUpdater.maybeAddListener()
    }

    private fun View.hasRequest() : Boolean {
        return this.getTag(tagID) != null
    }
}