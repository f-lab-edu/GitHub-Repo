package com.prac.githubrepo.main.request

interface Request {
    fun checkStarredState()
    fun cancel()
    fun isCompleted() : Boolean
}