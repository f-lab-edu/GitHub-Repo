package com.prac.data.source

import com.prac.data.repository.dto.RepoDto

internal interface RepoApiDataSource {
    suspend fun getRepos(
        userName: String
    ) : List<RepoDto>
}