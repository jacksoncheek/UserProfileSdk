package com.jacksoncheek.userprofile.sample

import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface Graph {

    val userProfileSdk: UserProfileSdk

    val dispatchers: UserProfileDispatchers

    val sdkSessionScope: CoroutineScope

    val viewModelFactory: ViewModelFactory

    class Builder {

        var userProfileSdk: UserProfileSdk? = null

        fun userProfileSdk(userProfileSdk: UserProfileSdk) = apply {
            this.userProfileSdk = userProfileSdk
        }

        fun build(): Graph {
            requireNotNull(userProfileSdk) {
                "`userProfileSdk` cannot be null"
            }

            val dispatchers = UserProfileDispatchers()

            val sdkSessionScope = CoroutineScope(dispatchers.ui + Job())

            val viewModelFactory = ViewModelFactory(
                scope = sdkSessionScope,
                userProfileSdk = userProfileSdk!!
            )

            return GraphImpl(
                GraphImpl.Params(
                    userProfileSdk = userProfileSdk!!,
                    dispatchers = dispatchers,
                    sdkSessionScope = sdkSessionScope,
                    viewModelFactory = viewModelFactory
                )
            )
        }
    }
}

/**
 * Implementation of the [Graph]
 */
private class GraphImpl(
    params: Params
) : Graph {

    override val userProfileSdk: UserProfileSdk by lazy {
        params.userProfileSdk
    }

    override val dispatchers: UserProfileDispatchers by lazy {
        params.dispatchers
    }

    override val sdkSessionScope: CoroutineScope by lazy {
        params.sdkSessionScope
    }

    override val viewModelFactory: ViewModelFactory by lazy {
        params.viewModelFactory
    }

    data class Params(
        val userProfileSdk: UserProfileSdk,
        val dispatchers: UserProfileDispatchers,
        val sdkSessionScope: CoroutineScope,
        val viewModelFactory: ViewModelFactory
    )
}