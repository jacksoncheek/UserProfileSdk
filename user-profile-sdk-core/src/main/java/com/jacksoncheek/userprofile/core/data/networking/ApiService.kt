package com.jacksoncheek.userprofile.core.data.networking

import com.jacksoncheek.userprofile.core.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/api")
    fun getUser(
        @Query("amount") amount: String,
        @Query("region") region: String,
        @Query("maxlen") maxlen: String,
        @Query("ext") ext: String
    ): Deferred<Response<User>>
}

interface Api {

    suspend fun getUser(): Response<User>
}