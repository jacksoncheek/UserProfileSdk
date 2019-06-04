package com.jacksoncheek.userprofile.core.data

import android.content.Context
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.internal.logic.Result
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import com.jacksoncheek.userprofile.core.data.model.User
import com.jacksoncheek.userprofile.core.data.networking.ApiService
import com.jacksoncheek.userprofile.core.data.networking.NetworkStack
import com.jacksoncheek.userprofile.core.data.networking.UserRemote
import com.jacksoncheek.userprofile.core.data.networking.UserRemoteImpl
import okhttp3.OkHttpClient

interface UserProfileSdkCore {

    val appContext: Context

    val baseUrl: BaseUrl

    val region: Region

    val okHttpBuilder: OkHttpClient.Builder

    val dispatchers: UserProfileDispatchers

    val logger: Logger

    val userRemote: UserRemote

    val networkStack: NetworkStack

    // No UI - just networking + data
    suspend fun getUser(): Result<User>

    private class Impl(private val params: Params) :
        UserProfileSdkCore {

        override val appContext: Context by lazy {
            params.appContext
        }

        override val baseUrl: BaseUrl by lazy {
            params.baseUrl
        }

        override val region: Region by lazy {
            params.region
        }

        override val okHttpBuilder: OkHttpClient.Builder by lazy {
            params.okHttpBuilder
        }

        override val dispatchers: UserProfileDispatchers by lazy {
            params.dispatchers
        }

        override val logger: Logger by lazy {
            params.logger
        }

        override val networkStack: NetworkStack by lazy {
            NetworkStack.builder()
                .baseUrl(params.baseUrl)
                .region(params.region)
                .networkContext(params.dispatchers.background)
                .apiService(ApiService::class.java)
                .okHttpClientBuilder(params.okHttpBuilder)
                .build()
        }

        override val userRemote: UserRemote by lazy {
            UserRemoteImpl(networkStack)
        }

        override suspend fun getUser(): Result<User> {
            return userRemote.getUser()
        }
    }

    companion object {
        fun create(params: Params): UserProfileSdkCore {
            return Impl(params)
        }
    }
}