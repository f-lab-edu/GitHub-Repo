package com.prac.githubrepo.main.request

interface Request {
    fun begin()
    fun cancel()
    fun isComplete() : Boolean
}