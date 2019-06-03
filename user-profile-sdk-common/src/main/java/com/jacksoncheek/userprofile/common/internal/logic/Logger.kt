package com.jacksoncheek.userprofile.common.internal.logic

import android.util.Log
import com.jacksoncheek.userprofile.common.typealiases.Tag

interface Logger {

    fun log(
        message: String,
        throwable: Throwable? = null
    )
}

class NoOpLogger : Logger {

    override fun log(
        message: String,
        throwable: Throwable?
    ) {
        // No-Op
    }
}

class AndroidLogger(private val tag: Tag = "UserProfileSdk") : Logger {

    override fun log(
        message: String,
        throwable: Throwable?
    ) {
        if (throwable != null) {
            Log.e(
                tag,
                message,
                throwable
            )
        } else {
            Log.d(
                tag,
                message
            )
        }
    }
}