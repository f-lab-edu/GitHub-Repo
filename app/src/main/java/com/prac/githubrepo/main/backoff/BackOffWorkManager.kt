package com.prac.githubrepo.main.backoff

interface BackOffWorkManager {
    fun addWork(
        uniqueID: String,
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 1_000, // 1 second
        maxDelay: Long = 100_000,// 100 second
        factor: Double = 2.0,
        work: suspend () -> Result<*>
    )

    fun clearWork()
}