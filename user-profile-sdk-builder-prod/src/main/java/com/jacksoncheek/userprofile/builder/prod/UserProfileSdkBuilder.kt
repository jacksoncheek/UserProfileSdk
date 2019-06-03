package com.jacksoncheek.userprofile.builder.prod

import android.content.Context
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.builder.internal.UserProfileSdkImpl
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.internal.logic.NoOpLogger
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import okhttp3.OkHttpClient

class UserProfileSdkBuilder {

    private lateinit var appContext: Context

    private val baseUrl: BaseUrl = "https://uinames.com"

    private val region: Region = "United States"

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    private val dispatchers: UserProfileDispatchers = UserProfileDispatchers()

    private val logger: Logger = NoOpLogger()

    fun appContext(appContext: Context) = apply {
        this.appContext = appContext
    }

    /** Builds the UserProfileSdkImpl. */
    fun build(): UserProfileSdk {

        requireNotNull(appContext) {
            "`appContext` cannot be null"
        }

        val params = Params(
            appContext = appContext,
            baseUrl = baseUrl,
            okHttpBuilder = okHttpBuilder,
            dispatchers = dispatchers,
            logger = logger,
            region = region
        )

        return UserProfileSdkImpl(
            params = params
        )
    }
}