package com.prac.githubrepo.main

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepoStarUpdater private constructor(
    private val repoRepository: RepoRepository,
    private val view: View,
    private var repoEntity: RepoEntity,
    private val uiStateUpdater: UiStateUpdater
) {
    private var isAttachedStateListenerAdded = false
    private val attachedStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            view.setCheckIsStarredJob()
        }

        override fun onViewDetachedFromWindow(view: View) {
            view.clearCheckIsStarredJob()
        }
    }

    private fun View.setCheckIsStarredJob() {

    }

    private fun View.clearCheckIsStarredJob() {
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
}