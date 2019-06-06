package com.jacksoncheek.userprofile.ui.logic

import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.jacksoncheek.userprofile.ui.view.Coordinator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor

/**
 * Allows observation of the User Profile flow. Use this class to:
 *
 * - start a User Profile flow.
 * - observe when an [UserProfileSdkResult] is ready to be delivered back to the client.
 */
class UserProfileStateRepo(
    scope: CoroutineScope,
    logger: Logger,
    coordinator: Coordinator
) : CoroutineScope by scope {

    private val stateChannel = ConflatedBroadcastChannel(State())

    private val actor = actor<Intention>(capacity = Channel.UNLIMITED) {
        var currentState = stateChannel.value

        for (intention in channel) {

            currentState = when (intention) {
                is Intention.Start -> {
                    logger.log("Sending start intention to coordinator")
                    coordinator.send(
                        Coordinator.Intention.Start
                    )

                    currentState.copy(
                        idle = false,
                        callback = intention.callback
                    )
                }

                is Intention.End -> {
                    currentState.callback(intention.result)

                    coordinator.send(
                        Coordinator.Intention.End
                    )

                    currentState.copy(
                        idle = true,
                        callback = { }
                    )
                }
            }
        }

        if (stateChannel.value != currentState) {
            stateChannel.send(currentState)
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
        val idle: Boolean = true, // Whether or not the flow has started
        val callback: (UserProfileSdkResult) -> Unit = { }
    )

    sealed class Intention {

        data class Start(
            val callback: (UserProfileSdkResult) -> Unit
        ) : Intention()

        data class End(
            val result: UserProfileSdkResult
        ) : Intention()
    }
}