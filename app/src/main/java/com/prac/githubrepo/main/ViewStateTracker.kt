package com.prac.githubrepo.main

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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

        }

        override fun onViewDetachedFromWindow(view: View) {

        }
    }
}