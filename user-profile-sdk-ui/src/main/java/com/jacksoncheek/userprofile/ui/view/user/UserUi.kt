package com.jacksoncheek.userprofile.ui.view.user

import com.jacksoncheek.userprofile.core.data.model.User
import kotlinx.coroutines.CoroutineScope

interface UserUi {

    fun observe(
        scope: CoroutineScope,
        listener: (UserProfileViewModel.Intention) -> Unit
    )

    fun showLoading(show: Boolean)

    fun showUserProfile(show: Boolean, user: User?)

    fun enableOkButton(enable: Boolean)
}

fun UserUi.render(state: UserProfileViewModel.State) {

    showLoading(state.isLoading)

    showUserProfile(
        show = state.userProfileIsAvailable,
        user = state.user
    )

    enableOkButton(state.enableOkButton)
}