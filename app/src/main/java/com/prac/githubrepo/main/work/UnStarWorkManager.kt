package com.prac.githubrepo.main.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnStarWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    @HiltWorker
    class UnStarWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val unStarRequest: UnStarRequest
    ) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result {
            // TODO doWork
            return Result.success()
        }

        interface UnStarRequest {
            suspend fun unStarRepository(userName: String, repoName: String)
        }
    }
}