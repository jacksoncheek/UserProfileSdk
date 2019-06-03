package com.jacksoncheek.userprofile.builder.internal

import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.builder.UserProfileSdkResultCallback
import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.core.data.UserProfileSdkCore
import com.jacksoncheek.userprofile.ui.model.UiParams
import com.jacksoncheek.userprofile.ui.UserProfileSdkUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserProfileSdkImpl(private val params: Params) : UserProfileSdk {

    private val sdkSession = CoroutineScope(params.dispatchers.background + Job())

    private val coreSdk = UserProfileSdkCore.create(params)

    private val uiParams = UiParams(
        params = params,
        coreSdk = coreSdk
    )

    private val uiSdk = UserProfileSdkUi.create(uiParams)

    override fun startFlow(
        callback: UserProfileSdkResultCallback
    ) {
        sdkSession.launch {
            uiSdk.startFlow(callback)
        }
    }
}