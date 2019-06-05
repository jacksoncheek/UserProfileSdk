package com.jacksoncheek.userprofile.core.data.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import com.jacksoncheek.userprofile.core.data.model.User
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.CoroutineContext

class NetworkStack private constructor(
    private val baseUrl: BaseUrl,
    private val region: Region,
    private val networkContext: CoroutineContext,
    private val apiServiceClass: Class<ApiService>,
    private val okHttpClientBuilder: OkHttpClient.Builder
) : Api {

    var client: Retrofit? = null

    var apiService: ApiService? = null

    val AMOUNT: String = "1"

    val MAX_LENGTH: String = "12"

    val EXTRA: String = "true"

    override suspend fun getUser(): Response<User> {
        val response = withContext(networkContext) {
            apiService().getUser(
                AMOUNT,
                region,
                MAX_LENGTH,
                EXTRA
            ).await()
        }

        return response
    }

    private fun client(): Retrofit {
        return if (client != null) {
            client!!
        } else {
            val okHttpClient = okHttpClientBuilder
                .addNetworkInterceptor(IoExceptionInterceptor())
                .build()

            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }

    private fun apiService(): ApiService {
        return if (apiService != null) {
            apiService!!
        } else {
            client().create(apiServiceClass)
        }
    }

    class Builder {
        private var baseUrl: BaseUrl? = null
        private var region: Region? = null
        private var networkContext: CoroutineContext = Dispatchers.Default
        private var apiServiceClass: Class<ApiService>? = null
        private var okHttpClientBuilder: OkHttpClient.Builder? = null

        fun baseUrl(baseUrl: BaseUrl) = apply {
            this.baseUrl = baseUrl
        }

        fun region(region: Region) = apply {
            this.region = region
        }

        fun networkContext(networkContext: CoroutineContext) = apply {
            this.networkContext = networkContext
        }

        fun apiService(apiServiceClass: Class<ApiService>) = apply {
            this.apiServiceClass = apiServiceClass
        }

        fun okHttpClientBuilder(okHttpClientBuilder: OkHttpClient.Builder) = apply {
            this.okHttpClientBuilder = okHttpClientBuilder
        }

        fun build(): NetworkStack {
            require(baseUrl != null) { "`baseUrl` is required" }
            require(region != null) { "`region` is required" }
            require(apiServiceClass != null) { "`apiServiceClass` is required" }
            require(okHttpClientBuilder != null) { "`okHttpClientBuilder` is required" }

            return NetworkStack(
                baseUrl = baseUrl!!,
                region = region!!,
                networkContext = networkContext,
                apiServiceClass = apiServiceClass!!,
                okHttpClientBuilder = okHttpClientBuilder!!
            )
        }
    }

    companion object {
        fun builder() = Builder()

        fun isNetworkConnected(context: Context): Boolean {
            val networkInfo = getNetworkInfo(context)

            return networkInfo.isConnected
        }

        fun isWifiConnected(context: Context): Boolean {
            val networkInfo = getNetworkInfo(context)

            return (ConnectivityManager.TYPE_WIFI == networkInfo.type) && networkInfo.isConnected
        }

        private fun getNetworkInfo(context: Context): NetworkInfo {
            val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectionManager.activeNetworkInfo
        }
    }
}