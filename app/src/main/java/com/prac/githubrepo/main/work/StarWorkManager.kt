package com.prac.githubrepo.main.work

import androidx.work.Constraints
import androidx.work.WorkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val constraints: Constraints
) {
}