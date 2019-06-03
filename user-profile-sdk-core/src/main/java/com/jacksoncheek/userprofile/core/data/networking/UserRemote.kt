package com.jacksoncheek.userprofile.core.data.networking

import com.jacksoncheek.userprofile.common.internal.logic.Result
import com.jacksoncheek.userprofile.core.data.model.User

interface UserRemote {

    suspend fun getUser() : Result<User>
}