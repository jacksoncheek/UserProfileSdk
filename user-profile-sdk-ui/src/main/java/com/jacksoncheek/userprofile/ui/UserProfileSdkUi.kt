package com.jacksoncheek.userprofile.ui

import com.jacksoncheek.userprofile.common.internal.model.Params
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.jacksoncheek.userprofile.core.data.UserProfileSdkCore
import com.jacksoncheek.userprofile.ui.base.ViewModelFactory
import com.jacksoncheek.userprofile.ui.logic.UserProfileStateRepo
import com.jacksoncheek.userprofile.ui.model.UiParams
import com.jacksoncheek.userprofile.ui.view.Coordinator
import com.jacksoncheek.userprofile.ui.view.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface UserProfileSdkUi {

    val params: Params

    val coreSdk: UserProfileSdkCore

    val navigator: Navigator

    val coordinator: Coordinator

    val userProfileStateRepo: UserProfileStateRepo

    val viewModelFactory: ViewModelFactory

    suspend fun startFlow(callback: (UserProfileSdkResult) -> Unit)

    private class Impl(private val uiParams: UiParams) : UserProfileSdkUi {

        private val uiScope = CoroutineScope(uiParams.params.dispatchers.ui + Job())

        private val backgroundScope = CoroutineScope(uiParams.params.dispatchers.background + Job())

        init {
            UserProfileSdkUi.instance = this
        }

        override val params: Params by lazy {
            uiParams.params
        }

        override val coreSdk: UserProfileSdkCore by lazy {
            uiParams.coreSdk
        }

        override val navigator: Navigator by lazy {
            Navigator(
                appContext = params.appContext,
                scope = uiScope,
                logger = params.logger
            )
        }

        override val coordinator: Coordinator by lazy {
            Coordinator(
                navigator = navigator,
                scope = uiScope
            )
        }

        override val userProfileStateRepo: UserProfileStateRepo by lazy {
            UserProfileStateRepo(
                scope = backgroundScope,
                logger = params.logger,
                coordinator = coordinator
            )
        }

        override val viewModelFactory: ViewModelFactory by lazy {
            ViewModelFactory(
                scope = uiScope,
                coreSdk = coreSdk,
                userProfileStateRepo = userProfileStateRepo,
                coordinator = coordinator
            )
        }

        override suspend fun startFlow(callback: (UserProfileSdkResult) -> Unit) {
            userProfileStateRepo.send(
                UserProfileStateRepo.Intention.Start(
                    callback = callback
                )
            )
        }
    }

    companion object {
        fun create(uiParams: UiParams): UserProfileSdkUi {
            return UserProfileSdkUi.Impl(uiParams)
        }

        lateinit var instance: UserProfileSdkUi internal set
    }
}