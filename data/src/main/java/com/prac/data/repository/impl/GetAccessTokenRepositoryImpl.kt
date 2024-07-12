package com.prac.data.repository.impl

import com.prac.data.entity.AccessTokenEntity
import com.prac.data.repository.dto.AccessTokenDto
import com.prac.data.repository.GetAccessTokenRepository
import com.prac.data.source.GetAccessTokenDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetAccessTokenRepositoryImpl @Inject constructor(
    private val getAccessTokenDataSource: GetAccessTokenDataSource
) : GetAccessTokenRepository {
    override suspend fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String
    ): Result<AccessTokenEntity> {
        try {
            val model = getAccessTokenDataSource.getAccessToken(
                clientID = clientID,
                clientSecret = clientSecret,
                code = code
            )

            return Result.success(
                AccessTokenEntity(
                    accessToken = model.accessToken,
                    tokenType = model.tokenType
                )
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}