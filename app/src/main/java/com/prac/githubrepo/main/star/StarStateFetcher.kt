package com.prac.githubrepo.main.star

import com.prac.data.entity.RepoEntity

interface StarStateFetcher {
    suspend fun fetchStarState(repoEntity: RepoEntity)
}