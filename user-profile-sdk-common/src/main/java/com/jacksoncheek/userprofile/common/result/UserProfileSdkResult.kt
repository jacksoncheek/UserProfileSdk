package com.jacksoncheek.userprofile.common.result

import com.jacksoncheek.userprofile.common.typealiases.UserName

sealed class UserProfileSdkResult {

    data class Success(val userName: UserName) : UserProfileSdkResult()

    data class Failure(val error: String) : UserProfileSdkResult()
}