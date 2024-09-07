package com.prac.githubrepo.main.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.prac.githubrepo.main.work.UnStarWorkManager.UnStarWorker.Companion.KEY_REPO_NAME
import com.prac.githubrepo.main.work.UnStarWorkManager.UnStarWorker.Companion.KEY_USER_NAME
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnStarWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {

    fun enqueueUnStarWorker(id: String, userName: String, repoName: String) {
        val data = Data.Builder()
            .putString(KEY_USER_NAME, userName)
            .putString(KEY_REPO_NAME, repoName)
            .build()

        val workRequestList = makeWorkRequestList(data)

        workManager.beginUniqueWork(
            id,
            ExistingWorkPolicy.REPLACE,
            workRequestList[0]
        ).then(workRequestList[1])
            .then(workRequestList[2])
            .enqueue()
    }

    /**
     * delay 시간이 30초, 60초, 120초인 [OneTimeWorkRequest] 리스트를 생성하는 메서드
     * 리스트의 사이즈는 3개로 고정되어 있음.
     */
    private fun makeWorkRequestList(data: Data) : List<OneTimeWorkRequest> {
        return ArrayList<OneTimeWorkRequest>(3).apply {
            var delaySeconds = 30L

            for (i in 1..3) {
                add(
                    OneTimeWorkRequestBuilder<UnStarWorker>()
                        .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .setInputData(data)
                        .build()
                )

                delaySeconds *= 2
            }
        }
    }

    @HiltWorker
    class UnStarWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val unStarRequest: UnStarRequest
    ) : CoroutineWorker(context, params) {

        companion object {
            const val KEY_USER_NAME = "userName"
            const val KEY_REPO_NAME = "repoName"
        }

        override suspend fun doWork(): Result {
            val userName = params.inputData.getString(KEY_USER_NAME)
            val repoName = params.inputData.getString(KEY_REPO_NAME)

            if (userName == null || repoName == null) return Result.failure()

            unStarRequest.unStarRepository(userName, repoName)

            return Result.success()
        }

        interface UnStarRequest {
            suspend fun unStarRepository(userName: String, repoName: String)
        }
    }
}