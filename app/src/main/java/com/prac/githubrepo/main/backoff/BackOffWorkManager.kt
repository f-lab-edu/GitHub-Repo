package com.prac.githubrepo.main.backoff

interface BackOffWorkManager {
    suspend fun addStarWorkWithUniqueID(uniqueID: Int, userName: String, repoName: String)

    suspend fun addUnStarWorkWithUniqueID(uniqueID: Int, userName: String, repoName: String)

    fun clearWork()
}