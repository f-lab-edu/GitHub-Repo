package com.prac.data.source.api

import android.util.Log
import com.prac.data.exception.GitHubApiException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class ErrorHandleInterceptor : Interceptor {
    private val tag = "ErrorHandlingInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response

        try {
            response = chain.proceed(chain.request())
        } catch (e: IOException) {
            Log.e(tag, "IOException occurred", e)
            throw GitHubApiException.NetworkException("네트워크 불안정")
        }

        when (response.code) {
            401 -> {
                Log.e(tag, "UnAuthorizedException error : ${response.message}")
                throw GitHubApiException.UnAuthorizedException("잘못된 토큰")
            }
        }

        return response
    }
}