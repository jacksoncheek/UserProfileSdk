package com.jacksoncheek.userprofile.ui.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.jacksoncheek.userprofile.common.internal.logic.Logger
import com.jacksoncheek.userprofile.common.result.UserProfileSdkResult
import com.jacksoncheek.userprofile.common.internal.logic.Result
import com.jacksoncheek.userprofile.ui.view.user.UserProfileActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.actor

/**
 * Navigator to start the User Profile UI.
 */
class Navigator(
    appContext: Context,
    scope: CoroutineScope,
    logger: Logger,
    activityLauncher: () -> Unit = { UserProfileActivity.Args().launch(appContext) }
) : CoroutineScope by scope {

    private var activity: AppCompatActivity? = null

    private val actor = actor<Intention>(start = CoroutineStart.UNDISPATCHED) {

        for (intention in channel) {
            when (intention) {
                is Intention.Start -> {
                    activityLauncher.invoke()
                }

                is Intention.End -> {
                    if (activity != null) {
                        activity!!.finish()
                        activity = null
                    } else  {
                        logger.log("Activity was null")
                    }
                }

                is Intention.StoreActivity -> {
                    activity = intention.activity
                }

                is Intention.GetActivity -> {
                    if (activity != null) {
                        intention.deferred.complete(
                            Result.Expected(
                                result = activity!!
                            )
                        )
                    } else {
                        intention.deferred.complete(
                            Result.Unexpected(
                                error = "Activity was null"
                            )
                        )
                    }
                }
            }
        }
    }

    fun send(intention: Intention) {
        actor.offer(intention)
    }

    sealed class Intention {

        data class Start(
            val callback: (UserProfileSdkResult) -> Unit
        ) : Intention()

        object End : Intention()

        data class StoreActivity(
            val activity: AppCompatActivity
        ) : Intention()

        data class GetActivity(
            val deferred: CompletableDeferred<Result<AppCompatActivity>> = CompletableDeferred()
        ) : Intention()
    }
}