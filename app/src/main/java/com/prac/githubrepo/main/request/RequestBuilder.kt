package com.prac.githubrepo.main.request

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import com.prac.githubrepo.main.RepoStarUpdater
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope

class RequestBuilder @AssistedInject constructor(
    private val repoRepository: RepoRepository,
    @Assisted private val scope: CoroutineScope
) {
    companion object {
        private val tagID = R.string.requestID
    }

    @AssistedFactory
    interface Factory {
        fun create(scope: CoroutineScope): RequestBuilder
    }

    private var view: View? = null
    private var repoEntity: RepoEntity? = null

    fun setView(view: View) : RequestBuilder = apply {
        this.view = view
    }

    fun setRepoEntity(repoEntity: RepoEntity) = apply {
        this.repoEntity = repoEntity
    }

    fun build() {
        val view = checkNotNull(view)
        val repoEntity = checkNotNull(repoEntity)

        val updater = RepoStarUpdater(
            request = StarRequest(
                repoRepository = repoRepository,
                repoEntity = repoEntity,
                scope = scope
            ),
            view = view
        )

        if (view.hasUpdaterTag()) (view.getTag(tagID) as RepoStarUpdater).maybeRemoveListener()

        view.setTag(tagID, updater)

        if (repoEntity.isStarred == null) updater.addListener()

        clear()
    }

    private fun View.hasUpdaterTag() : Boolean {
        val tag = getTag(tagID) ?: return false

        if (tag !is Request) throw IllegalStateException("Tag is not of type Request")

        return true
    }

    private fun clear() = apply {
        this.view = null
        this.repoEntity = null
    }
}