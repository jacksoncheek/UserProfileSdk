package com.jacksoncheek.userprofile.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jacksoncheek.userprofile.core.data.UserProfileSdkCore
import com.jacksoncheek.userprofile.ui.logic.UserProfileStateRepo
import com.jacksoncheek.userprofile.ui.view.Coordinator
import com.jacksoncheek.userprofile.ui.view.user.UserProfileViewModel
import kotlinx.coroutines.CoroutineScope

/**
 * The [ViewModelProvider.Factory] that will create [ViewModel]s to views that request them.
 */
class ViewModelFactory(
    private val scope: CoroutineScope,
    private val coreSdk: UserProfileSdkCore,
    private val userProfileStateRepo: UserProfileStateRepo,
    private val coordinator: Coordinator
) : ViewModelProvider.Factory {

    /**
     * Creates the correct [ViewModel] for a given [ViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {

            UserProfileViewModel::class.java -> provideUserProfileViewModel()

            else -> throw IllegalArgumentException("ViewModel $modelClass is not supported by this factory.")
        }

        return viewModel as T
    }

    private fun provideUserProfileViewModel(): UserProfileViewModel {
        return UserProfileViewModel(
            scope = scope,
            coreSdk = coreSdk,
            userProfileStateRepo = userProfileStateRepo,
            coordinator = coordinator
        )
    }
}