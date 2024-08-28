package com.prac.githubrepo.main

import android.view.View
import com.prac.githubrepo.main.request.Request

class RepoStarUpdater(
    private val request: Request,
    private val view: View
) {
    private var isAttachedStateListenerAdded = false
    private val attachedStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            startUpdatingStarState()
        }

        override fun onViewDetachedFromWindow(view: View) {
            cancelUpdatingStarState()
        }
    }

    private fun startUpdatingStarState() {
        request.checkStarredState()
    }

    private fun cancelUpdatingStarState() {
        request.cancel()

        maybeRemoveListener()
    }

    fun maybeAddListener() {
        view.removeOnAttachStateChangeListener(attachedStateListener)
        view.addOnAttachStateChangeListener(attachedStateListener)
    }

    fun maybeRemoveListener() {
        if (!isAttachedStateListenerAdded) return

        isAttachedStateListenerAdded = false
        view.removeOnAttachStateChangeListener(attachedStateListener)
    }
}