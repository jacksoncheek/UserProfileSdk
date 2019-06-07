package com.jacksoncheek.userprofile.sample

import android.content.Context
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.builder.dev.UserProfileSdkBuilder
import com.jacksoncheek.userprofile.common.internal.logic.AndroidLogger
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import com.readystatesoftware.chuck.ChuckInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A graph for bindings specific to the MOCK flavor.
 */
interface ConfigurableGraph {

    val baseUrl: BaseUrl

    val logger: Logger

    val region: Region

    val okHttpBuilder: OkHttpClient.Builder

    val dispatchers: UserProfileDispatchers

    val userProfileSdk: UserProfileSdk

    private class Impl(private val appContext: Context) : ConfigurableGraph {

        override val baseUrl: String
            get() {
                return "http://localhost:8080"
            }

        override val logger by lazy { AndroidLogger() }

        override val region by lazy { "United States" }

        override val okHttpBuilder by lazy {
            OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ChuckInterceptor(appContext).showNotification(true))
        }

        override val dispatchers by lazy {
            UserProfileDispatchers()
        }

        private val mockScope: CoroutineScope by lazy {
            CoroutineScope(dispatchers.io)
        }

        private val mockDispatcher: MockDispatcher by lazy {
            MockDispatcher()
        }

        private val mockWebServer: MockWebServer = MockWebServer().apply {
            setDispatcher(mockDispatcher)
            mockScope.launch {
                start(8080)
            }
        }

        override val userProfileSdk by lazy {
            UserProfileSdkBuilder()
                .appContext(appContext)
                .baseUrl(baseUrl)
                .region(region)
                .okHttpBuilder(okHttpBuilder)
                .dispatchers(dispatchers)
                .logger(logger)
                .build()
        }
    }

    companion object {
        fun create(appContext: Context): ConfigurableGraph {
            return ConfigurableGraph.Impl(appContext)
        }
    }
}