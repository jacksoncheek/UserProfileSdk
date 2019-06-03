package com.jacksoncheek.userprofile.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jacksoncheek.userprofile.builder.dev.UserProfileSdkBuilder
import com.jacksoncheek.userprofile.common.internal.logic.AndroidLogger
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userProfileSdk = UserProfileSdkBuilder()
            .appContext(this)
            .baseUrl("https://uinames.com")
            .region("United States")
            .okHttpBuilder(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(ChuckInterceptor(this).showNotification(true))
            )
            .dispatchers(UserProfileDispatchers())
            .logger(AndroidLogger())
            .build()

        userProfileSdk.startFlow { result ->
            when (result) {
                is UserProfileSdkResult.Success -> {
                    Log.d("MainActivity", "User: ${result.userName}")
                }

                is UserProfileSdkResult.Failure -> {
                    Log.d("MainActivity", "Oops! We hit a snag... ${result.error}")
                }
            }
        }
    }
}
