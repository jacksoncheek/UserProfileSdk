package com.jacksoncheek.userprofile.sample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import kotlinx.coroutines.CoroutineScope

/**
 * The [ViewModelProvider.Factory] that will create [ViewModel]s to views that request them.
 */
class ViewModelFactory(
    private val scope: CoroutineScope,
    private val userProfileSdk: UserProfileSdk
) : ViewModelProvider.Factory {

    /**
     * Creates the correct [ViewModel] for a given [ViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {

            MainViewModel::class.java -> provideMainVieModel()

            else -> throw IllegalArgumentException("ViewModel $modelClass is not supported by this factory.")
        }

        return viewModel as T
    }

    private fun provideMainVieModel(): MainViewModel {
        return MainViewModel(
            scope = scope,
            userProfileSdk = userProfileSdk
        )
    }
}