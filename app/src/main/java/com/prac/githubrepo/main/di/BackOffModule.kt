package com.prac.githubrepo.main.di

import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.backoff.BackOffWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlin.math.pow

@Module
@InstallIn(SingletonComponent::class)
class BackOffModule {
    @Provides
    @Singleton
    fun provideBackOffWorkManager(
        repoRepository: RepoRepository
    ) : BackOffWorkManager {
        return object : BackOffWorkManager {
            private val _workMap = mutableMapOf<Int, Job>()

            private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

            private val attemptTimes = 8
            private val multiplier = 2.0
            private val secondsToMillis = 1000

            override suspend fun addStarWorkWithUniqueID(uniqueID: Int, userName: String, repoName: String) {
                TODO("Not yet implemented")
            }

            override suspend fun addUnStarWorkWithUniqueID(uniqueID: Int, userName: String, repoName: String) {
                TODO("Not yet implemented")
            }

            override fun clearWork() {
                TODO("Not yet implemented")
            }

            private fun calculateExponentialBackOffDelay(attemptTime: Int) : Long =
                multiplier.pow(attemptTime).toLong() * secondsToMillis
        }
    }
}