package com.prac.githubrepo.main

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewStateTracker private constructor(
    private val repoRepository: RepoRepository,
    private val view: View,
    private var repoEntity: RepoEntity,
    private val uiStateUpdater: UiStateUpdater
) {
    companion object {
        private val jobID = R.string.jobID
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private var isAttachedStateListenerAdded = false
    private val attachedStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            view.setCheckIsStarredJob()
        }

        override fun onViewDetachedFromWindow(view: View) {
            view.clearCheckIsStarredJob()
        }
    }

    private fun View.getJob() : Job? {
        val tag = getTag(jobID)

        if (tag is Job) {
            return tag
        }

        return null
    }

    private fun View.setCheckIsStarredJob() {
        setTag(
            jobID,
            scope.launch {
                repoRepository.isStarred(repoEntity.name)
                    .onSuccess {
                        uiStateUpdater.updateIsStarred(repoEntity.id, it)
                    }.onFailure {
                        uiStateUpdater.updateIsStarred(repoEntity.id, false)
                    }
            }
        )
    }

    private fun View.clearCheckIsStarredJob() {
        getJob()?.let {
            setTag(jobID, null)
            it.cancel()
        }

        maybeRemoveListener()
    }

    private fun maybeAddListener() {
        if (isAttachedStateListenerAdded || repoEntity.isStarred != null) return

        view.addOnAttachStateChangeListener(attachedStateListener)
        isAttachedStateListenerAdded = true
    }

    private fun updateAndMaybeAddListener(repoEntity: RepoEntity) {
        this.repoEntity = repoEntity

        maybeAddListener()
    }

    private fun maybeRemoveListener() {
        if (!isAttachedStateListenerAdded) return

        view.removeOnAttachStateChangeListener(attachedStateListener)
        isAttachedStateListenerAdded = false
    }

    class Builder @Inject constructor(
        private val repoRepository: RepoRepository,
    ) {
        companion object {
            private val viewStateTrackerID = R.string.viewStateTrackerID
        }
        private var uiStateUpdater: UiStateUpdater? = null
        private var repoEntity: RepoEntity? = null
        private var view: View? = null

        fun setUiStateUpdater(uiStateUpdater: UiStateUpdater) = apply {
            this.uiStateUpdater = uiStateUpdater
        }

        fun setRepoEntity(repoEntity: RepoEntity) = apply {
            this.repoEntity = repoEntity
        }

        fun setView(view: View) = apply {
            this.view = view
        }
    }
}