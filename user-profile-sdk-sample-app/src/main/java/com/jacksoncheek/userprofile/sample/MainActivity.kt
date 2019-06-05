package com.jacksoncheek.userprofile.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult

class MainActivity : AppCompatActivity() {

    protected val mainApplication get() = this.application as MainApplication

    protected val graph get() = mainApplication.graph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userProfileSdk = graph.userProfileSdk

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
