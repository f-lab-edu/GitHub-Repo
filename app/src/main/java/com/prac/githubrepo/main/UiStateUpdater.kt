package com.prac.githubrepo.main

interface UiStateUpdater {
    fun updateIsStarred(id: Int, isStarred: Boolean)
}