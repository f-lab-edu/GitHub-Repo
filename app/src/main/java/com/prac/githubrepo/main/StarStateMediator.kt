package com.prac.githubrepo.main

import kotlinx.coroutines.flow.StateFlow

interface StarStateMediator {
    val starStates: StateFlow<List<StarState>>

    fun addStarState(id: Int, isStarred: Boolean, stargazersCount: Int)

    fun updateStarState(id: Int, isStarred: Boolean, stargazersCount: Int)

    fun clearStarState()

    data class StarState(
        val id: Int,
        val isStarred: Boolean,
        val stargazersCount: Int
    )
}