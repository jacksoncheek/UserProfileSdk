package com.jacksoncheek.userprofile.sample

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jacksoncheek.userprofile.common.typealiases.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainApplication get() = this.application as MainApplication

    private val graph get() = mainApplication.graph

    private val viewModelFactory by lazy {
        graph.viewModelFactory
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    private val ui: Ui by lazy {
        Ui(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ui.observe(viewModel.scope) { intention -> viewModel.send(intention) }

        viewModel.scope.launch {
            viewModel.states().consumeEach { viewState -> ui.render(viewState) }
        }
    }

    private inner class Ui(private val activity: MainActivity) : MainUi {

        private val name = activity.findViewById<TextView>(R.id.app_user_name)

        private val getUserButton = activity.findViewById<Button>(R.id.get_profile_button)

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

        private var listener: ((MainViewModel.Intention) -> Unit)? = null

        @Suppress("NOTHING_TO_INLINE")
        private inline fun notify(intention: MainViewModel.Intention) {
            listener?.invoke(intention)
        }

        private fun setUpListeners() {
            getUserButton.setOnClickListener {
                notify(MainViewModel.Intention.UserClickedGetUser)
            }
        }

        private fun removeListeners() {
            getUserButton.setOnClickListener(null)
        }
    }
}
