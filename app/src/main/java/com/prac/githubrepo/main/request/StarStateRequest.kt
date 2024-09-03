package com.prac.githubrepo.main.request

interface StarStateRequest {
    fun fetchStarState()
    fun cancel()
    fun isCompleted() : Boolean
}