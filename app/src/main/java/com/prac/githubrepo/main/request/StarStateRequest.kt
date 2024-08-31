package com.prac.githubrepo.main.request

interface StarStateRequest {
    fun checkStarredState()
    fun cancel()
    fun isCompleted() : Boolean
}