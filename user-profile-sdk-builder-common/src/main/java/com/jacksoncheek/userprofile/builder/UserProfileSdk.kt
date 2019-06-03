package com.jacksoncheek.userprofile.builder

import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult

interface UserProfileSdk {

    /** Begins the UserProfileSdk flow.
     *
     * @param callback invoked asynchronously after completion.
     */
    fun startFlow(
        callback: UserProfileSdkResultCallback
    )
}

typealias UserProfileSdkResultCallback = (UserProfileSdkResult) -> Unit