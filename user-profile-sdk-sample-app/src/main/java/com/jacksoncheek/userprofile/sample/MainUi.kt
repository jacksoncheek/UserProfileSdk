package com.jacksoncheek.userprofile.sample

import com.jacksoncheek.userprofile.common.typealiases.UserName
import kotlinx.coroutines.CoroutineScope

interface MainUi {

    fun observe(
        scope: CoroutineScope,
        listener: (MainViewModel.Intention) -> Unit
    )

    fun showUserName(userName: UserName)

    fun showError(error: String)

    fun enableGetUserButton(enable: Boolean)
}

fun MainUi.render(state: MainViewModel.State) {

    if (!state.userName.isNullOrEmpty()) {
        showUserName(state.userName)
    }

    if (!state.error.isNullOrEmpty()) {
        showError(state.error)
    }

    enableGetUserButton(state.enableGetUserButton)
}