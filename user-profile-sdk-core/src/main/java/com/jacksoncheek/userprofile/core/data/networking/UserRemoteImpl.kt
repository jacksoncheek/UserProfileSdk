package com.jacksoncheek.userprofile.core.data.networking

import com.jacksoncheek.userprofile.common.internal.logic.Result
import com.jacksoncheek.userprofile.core.data.model.User
import retrofit2.Response
import java.io.IOException

/**
 * Makes all network calls for the app and returns a [Result<T>].
 */
class UserRemoteImpl(
    private val networkStack: NetworkStack
) : UserRemote {

    override suspend fun getUser(): Result<User> {
        return try {
            networkStack.getUser()
                .getResult()
        } catch (e: IOException) {
            Result.Unexpected(e.localizedMessage)
        } catch (e: Throwable) {
            Result.Unexpected(e.localizedMessage)
        }
    }

}

fun <T : Any> Response<T>.getResult(): Result<T> =
    if (isSuccessful) {
        Result.Expected(body()!!)
    } else {
        Result.Unexpected(errorBody()!!.string())
    }