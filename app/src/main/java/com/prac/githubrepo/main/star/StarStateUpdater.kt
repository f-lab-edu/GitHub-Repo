package com.prac.githubrepo.main.star

interface StarStateUpdater {
    fun updateStarState(id: Int, isStarred: Boolean)
}