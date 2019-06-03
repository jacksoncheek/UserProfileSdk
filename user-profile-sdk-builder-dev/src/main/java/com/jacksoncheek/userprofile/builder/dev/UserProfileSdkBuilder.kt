package com.jacksoncheek.userprofile.builder.dev

import android.content.Context
import android.text.PrecomputedText
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.builder.internal.UserProfileSdkImpl
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import okhttp3.OkHttpClient

class UserProfileSdkBuilder {

    private lateinit var appContext: Context

    private lateinit var baseUrl: BaseUrl

    private lateinit var region: Region

    private lateinit var okHttpBuilder: OkHttpClient.Builder

    private lateinit var dispatchers: UserProfileDispatchers

    private lateinit var logger: Logger

    fun appContext(appContext: Context) = apply {
        this.appContext = appContext
    }

    fun baseUrl(baseUrl: BaseUrl) = apply {
        this.baseUrl = baseUrl
    }

    fun region(region: Region) = apply {
        this.region = region
    }

    fun okHttpBuilder(okHttpBuilder: OkHttpClient.Builder) = apply {
        this.okHttpBuilder = okHttpBuilder
    }

    fun dispatchers(dispatchers: UserProfileDispatchers) = apply {
        this.dispatchers = dispatchers
    }

    fun logger(logger: Logger) = apply {
        this.logger = logger
    }

    /** Builds the UserProfileSdkImpl. */
    fun build(): UserProfileSdk {

        requireNotNull(appContext) {
            "`appContext` cannot be null"
        }

        requireNotNull(baseUrl) {
            "`baseUrl` cannot be null"
        }

        requireNotNull(region) {
            "`region` cannot be null"
        }

        requireNotNull(okHttpBuilder) {
            "`okHttpBuilder` cannot be null"
        }

        requireNotNull(dispatchers) {
            "`dispatchers` cannot be null"
        }

        requireNotNull(logger) {
            "`logger` cannot be null"
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