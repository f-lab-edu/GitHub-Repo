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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                _workMap[uniqueID]?.cancel()

                val job = coroutineScope.launch {
                    repeat(attemptTimes) { attemptTime ->
                        delay(calculateExponentialBackOffDelay(attemptTime))

                        repoRepository.starRepository(userName, repoName)
                            .onSuccess { _workMap[uniqueID]?.cancel() }
                    }
                }

                _workMap[uniqueID] = job
            }

            override suspend fun addUnStarWorkWithUniqueID(uniqueID: Int, userName: String, repoName: String) {
                _workMap[uniqueID]?.cancel()

                val job = coroutineScope.launch {
                    repeat(attemptTimes) { attemptTime ->
                        delay(calculateExponentialBackOffDelay(attemptTime))

                        repoRepository.unStarRepository(userName, repoName)
                            .onSuccess { _workMap[uniqueID]?.cancel() }
                    }
                }

                _workMap[uniqueID] = job
            }

            override fun clearWork() {
                _workMap.forEach { if (!it.value.isCompleted) it.value.cancel() }

                _workMap.clear()
            }

            private fun calculateExponentialBackOffDelay(attemptTime: Int) : Long =
                multiplier.pow(attemptTime).toLong() * secondsToMillis
        }
    }
}