package com.prac.githubrepo.main

import android.view.View
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.R
import com.prac.githubrepo.main.request.Request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepoStarUpdater (
    private var request: Request,
    private val view: View,
) {
    private var isAttachedStateListenerAdded = false
    private val attachedStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            beginRequest()
        }

        override fun onViewDetachedFromWindow(view: View) {
            cancelRequest()
        }
    }

    private fun beginRequest() {
        request.begin()
    }

    private fun cancelRequest() {
        request.cancel()
        maybeRemoveListener()
    }

    private fun maybeAddListener() {
        if (isAttachedStateListenerAdded) return

        view.addOnAttachStateChangeListener(attachedStateListener)
        isAttachedStateListenerAdded = true
    }

    fun addOrUpdateListener(request: Request) {
        this.request = request

        maybeAddListener()
    }

    private fun maybeRemoveListener() {
        if (!isAttachedStateListenerAdded) return

        view.removeOnAttachStateChangeListener(attachedStateListener)
        isAttachedStateListenerAdded = false
    }
}