package com.prac.data.source

import com.prac.data.repository.model.RepoModel

internal interface RepoApiDataSource {
    suspend fun getRepos(
        userName: String
    ) : List<RepoModel>
}