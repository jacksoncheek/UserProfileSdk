package com.jacksoncheek.userprofile.ui.model

import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.core.data.UserProfileSdkCore

data class UiParams(
    val params: Params,
    val coreSdk: UserProfileSdkCore
)