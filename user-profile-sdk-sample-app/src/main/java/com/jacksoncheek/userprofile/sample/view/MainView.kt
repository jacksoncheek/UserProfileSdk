package com.jacksoncheek.userprofile.sample.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.jacksoncheek.userprofile.common.typealiases.UserName
import com.jacksoncheek.userprofile.sample.MainUi
import com.jacksoncheek.userprofile.sample.MainViewModel
import com.jacksoncheek.userprofile.sample.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

internal class MainView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet), MainUi {

    private var name: TextView

    private var getUserButton: Button

    init {
        LayoutInflater.from(getContext())
            .inflate(R.layout.sample_fragment_main, this)

        name = rootView.findViewById(R.id.app_user_name)

        getUserButton = rootView.findViewById(R.id.get_profile_button)
    }

    private var listener: ((MainViewModel.Intention) -> Unit)? = null

    private fun setUpListeners() {
        getUserButton.setOnClickListener {
            notify(MainViewModel.Intention.UserClickedGetUser)
        }
    }

    private fun removeListeners() {
        getUserButton.setOnClickListener(null)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun notify(intention: MainViewModel.Intention) {
        listener?.invoke(intention)
    }

    override fun observe(scope: CoroutineScope, listener: (MainViewModel.Intention) -> Unit) {
        setUpListeners()
        this.listener = listener

        scope.coroutineContext[Job]!!.invokeOnCompletion {
            removeListeners()
            this.listener = null
        }
    }

    override fun showUserName(userName: UserName) {
        name.text = userName
    }

    override fun showError(error: String) {
        name.text = error
    }

    override fun enableGetUserButton(enable: Boolean) {
        getUserButton.isEnabled = enable
    }
}