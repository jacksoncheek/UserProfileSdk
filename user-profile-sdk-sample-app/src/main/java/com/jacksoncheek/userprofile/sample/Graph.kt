package com.jacksoncheek.userprofile.sample

import com.jacksoncheek.userprofile.builder.UserProfileSdk

interface Graph {

    val userProfileSdk: UserProfileSdk

    class Builder {

        var userProfileSdk: UserProfileSdk? = null

        fun userProfileSdk(userProfileSdk: UserProfileSdk) = apply {
            this.userProfileSdk = userProfileSdk
        }

        fun build(): Graph {
            requireNotNull(userProfileSdk) {
                "`userProfileSdk` cannot be null"
            }

            return GraphImpl(
                GraphImpl.Params(
                    userProfileSdk = userProfileSdk!!
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

    data class Params(
        val userProfileSdk: UserProfileSdk
    )
}