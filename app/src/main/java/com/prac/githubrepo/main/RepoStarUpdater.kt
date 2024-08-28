package com.prac.githubrepo.main

import android.view.View
import com.prac.githubrepo.main.request.Request

class RepoStarUpdater(
    private val request: Request,
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
        request.checkStarredState()
    }

    private fun cancelUpdatingStarState() {
        request.cancel()

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