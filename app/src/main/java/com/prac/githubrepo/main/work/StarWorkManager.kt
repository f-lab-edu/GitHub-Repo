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
class StarWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
    @HiltWorker
    class StarWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val starRequest: StarRequest
    ) : CoroutineWorker(context, params) {

        companion object {
            const val KEY_USER_NAME = "userName"
            const val KEY_REPO_NAME = "repoName"
        }

        override suspend fun doWork(): Result {
            val userName = params.inputData.getString(KEY_USER_NAME)
            val repoName = params.inputData.getString(KEY_REPO_NAME)

            if (userName == null || repoName == null) return Result.failure()

            starRequest.starRepository(userName, repoName)

            return Result.success()
        }

        interface StarRequest {
            suspend fun starRepository(userName: String, repoName: String)
        }
    }
}