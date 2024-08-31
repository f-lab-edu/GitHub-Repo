package com.prac.githubrepo.main.star

import com.prac.data.entity.RepoEntity

interface StarStateUpdater {
    suspend fun updateStarState(repoEntity: RepoEntity)
}