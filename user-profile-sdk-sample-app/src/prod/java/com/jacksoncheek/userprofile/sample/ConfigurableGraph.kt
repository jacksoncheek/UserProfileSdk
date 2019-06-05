package com.jacksoncheek.userprofile.sample

import android.content.Context
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.builder.prod.UserProfileSdkBuilder
import com.jacksoncheek.userprofile.common.internal.logic.AndroidLogger
import com.jacksoncheek.userprofile.common.internal.logic.Logger

/**
 * A graph for bindings specific to the PROD flavor.
 */
interface ConfigurableGraph {

    val userProfileSdk: UserProfileSdk

    private class Impl(private val appContext: Context) : ConfigurableGraph {

        override val userProfileSdk by lazy {
            UserProfileSdkBuilder()
                .appContext(appContext)
                .build()
        }
    }

    companion object {
        fun create(appContext: Context): ConfigurableGraph {
            return ConfigurableGraph.Impl(appContext)
        }
    }
}