package com.prac.githubrepo.main

import kotlinx.coroutines.flow.StateFlow

interface StarStateMediator {
    val starStates: StateFlow<List<Pair<Int, Boolean>>>

    fun addStarState(id: Int, isStarred: Boolean)
}