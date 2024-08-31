package com.prac.githubrepo.main

import android.view.View
import com.prac.githubrepo.main.request.StarStateRequest

class RepoStarUpdater(
    private val starStateRequest: StarStateRequest,
    private val view: View
) {
    private val attachedStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            startUpdatingStarState()
        }

        override fun onViewDetachedFromWindow(view: View) {
            cancelUpdatingStarState()
        }
    }

    private fun startUpdatingStarState() {
        starStateRequest.checkStarredState()
    }

    private fun cancelUpdatingStarState() {
        starStateRequest.cancel()

        removeListener()
    }

    fun addListener() {
        view.removeOnAttachStateChangeListener(attachedStateListener)
        view.addOnAttachStateChangeListener(attachedStateListener)
    }

    fun removeListener() {
        view.removeOnAttachStateChangeListener(attachedStateListener)
    }
}