package com.jacksoncheek.userprofile.sample

import android.arch.lifecycle.ViewModel
import com.jacksoncheek.userprofile.builder.UserProfileSdk
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.jacksoncheek.userprofile.common.typealiases.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

class MainViewModel(
    val scope: CoroutineScope,
    val userProfileSdk: UserProfileSdk
) : ViewModel(), CoroutineScope by scope {

    private val stateChannel = ConflatedBroadcastChannel(State())

    private val actor = actor<Intention>(capacity = Channel.UNLIMITED) {
        var currentState = stateChannel.value

        for (intention in channel) {
            currentState = when (intention) {
                is Intention.UserClickedGetUser -> {
                    launch {
                        userProfileSdk.startFlow { result ->
                            send(
                                Intention.ResultReceived(result)
                            )
                        }
                    }

                    currentState.copy(
                        enableGetUserButton = false,
                        userName = null,
                        error = null
                    )
                }

                is Intention.ResultReceived -> {
                    when (intention.result) {
                        is UserProfileSdkResult.Success -> {
                            currentState.copy(
                                enableGetUserButton = true,
                                userName = intention.result.userName
                            )
                        }

                        is UserProfileSdkResult.Failure -> {
                            currentState.copy(
                                enableGetUserButton = true,
                                error = intention.result.error
                            )
                        }
                    }
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
        val enableGetUserButton: Boolean = true,
        val userName: UserName? = null,
        val error: String? = null
    )

    sealed class Intention {

        object UserClickedGetUser: Intention()

        data class ResultReceived(
            val result: UserProfileSdkResult
        ) : Intention()
    }
}