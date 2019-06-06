package com.jacksoncheek.userprofile.ui.view.user

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.jacksoncheek.userprofile.core.data.UserProfileSdkCore
import com.jacksoncheek.userprofile.common.internal.logic.Result
import com.jacksoncheek.userprofile.core.data.model.User
import com.jacksoncheek.userprofile.ui.logic.UserProfileStateRepo
import com.jacksoncheek.userprofile.ui.view.Coordinator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

class UserProfileViewModel(
    val scope: CoroutineScope,
    coreSdk: UserProfileSdkCore,
    userProfileStateRepo: UserProfileStateRepo,
    coordinator: Coordinator
) : ViewModel(), CoroutineScope by scope {

    init {
        launch {
            val userResult = coreSdk.getUser()

            when (userResult) {

                is Result.Expected -> {
                    send(
                        Intention.UserProfileReceived(
                            user = userResult.result
                        )
                    )
                }

                is Result.Unexpected -> {
                    send(Intention.ErrorReceived)
                }
            }
        }
    }

    private val stateChannel = ConflatedBroadcastChannel(State())

    private val actor = actor<Intention>(capacity = Channel.UNLIMITED) {
        var currentState = stateChannel.value

        for (intention in channel) {
            currentState = when (intention) {
                is Intention.UserProfileReceived -> {
                    currentState.copy(
                        isLoading = false,
                        user = intention.user,
                        userProfileIsAvailable = true,
                        enableGotItButton = true
                    )
                }

                is Intention.ErrorReceived -> {
                    launch {
                        coordinator.send(
                            Coordinator.Intention.End
                        )
                    }

                    launch {
                        val userProfileSdkResult = UserProfileSdkResult.Failure(
                            error = "Oops... Something went wrong!"
                        )

                        userProfileStateRepo.send(
                            UserProfileStateRepo.Intention.End(
                                userProfileSdkResult
                            )
                        )
                    }

                    currentState.copy(
                        isLoading = false
                    )
                }

                is Intention.UserClickedGotIt -> {
                    launch {
                        val userProfileSdkResult = UserProfileSdkResult.Success(
                            userName = "${currentState.user!!.name} ${currentState.user!!.surname}"
                        )

                        userProfileStateRepo.send(
                            UserProfileStateRepo.Intention.End(
                                userProfileSdkResult
                            )
                        )
                    }

                    launch {
                        coordinator.send(
                            Coordinator.Intention.End
                        )
                    }

                    currentState
                }

                is Intention.StoreActivity -> {
                    coordinator.send(
                        Coordinator.Intention.StoreActivity(
                            activity = intention.activity
                        )
                    )

                    currentState
                }
            }

            if (stateChannel.value != currentState) {
                stateChannel.send(currentState)
            }
        }
    }

    fun send(intention: Intention) {
        actor.offer(intention)
    }

    /**
     * Observe the [State].
     */
    fun states(): ReceiveChannel<State> = stateChannel.openSubscription()

    data class State(
        val isLoading: Boolean = true,
        val enableGotItButton: Boolean = false,
        val user: User? = null,
        val userProfileIsAvailable: Boolean = false
    )

    sealed class Intention {

        data class UserProfileReceived(
            val user: User
        ) : Intention()

        object ErrorReceived : Intention()

        object UserClickedGotIt: Intention()

        data class StoreActivity(
            val activity: AppCompatActivity
        ) : Intention()
    }
}