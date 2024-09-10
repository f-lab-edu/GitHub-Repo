package com.prac.data.source.network

import com.prac.data.repository.model.RepoDetailModel
import com.prac.data.repository.model.RepoModel

internal interface RepoApiDataSource {
    suspend fun getRepositories(userName: String, perPage:Int, page: Int) : List<RepoModel>

    suspend fun getRepository(userName: String, repoName: String) : RepoDetailModel
}