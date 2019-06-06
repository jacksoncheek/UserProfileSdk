package com.jacksoncheek.userprofile.ui.view

import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor

/**
 * Coordinator for the entire User Profile flow.
 */
class Coordinator(
    navigator: Navigator,
    scope: CoroutineScope
) : CoroutineScope by scope {

    private val actor = actor<Intention>(
        start = CoroutineStart.LAZY,
        capacity = Channel.UNLIMITED
    ) {

        for (intention in channel) {

            when (intention) {
                is Intention.Start -> {
                    navigator.send(
                        Navigator.Intention.Start
                    )
                }

                is Intention.End -> {
                    navigator.send(
                        Navigator.Intention.End
                    )
                }

                is Intention.StoreActivity -> {
                    navigator.send(
                        Navigator.Intention.StoreActivity(
                            activity = intention.activity
                        )
                    )
                }
            }
        }
    }

    fun send(intention: Intention) {
        actor.offer(intention)
    }

    sealed class Intention {

        object Start : Intention()

        object End : Intention()

        data class StoreActivity(
            val activity: AppCompatActivity
        ) : Intention()
    }
}