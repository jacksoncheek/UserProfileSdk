package com.jacksoncheek.userprofile.common.internal.model

import android.content.Context
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.internal.logic.UserProfileDispatchers
import com.jacksoncheek.userprofile.common.typealiases.BaseUrl
import com.jacksoncheek.userprofile.common.typealiases.Region
import okhttp3.OkHttpClient

data class Params(
    val appContext: Context,
    val baseUrl: BaseUrl,
    val okHttpBuilder: OkHttpClient.Builder,
    val dispatchers: UserProfileDispatchers,
    val logger: Logger,
    val region: Region
)