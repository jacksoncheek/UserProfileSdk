package com.jacksoncheek.userprofile.sample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacksoncheek.userprofile.common.typealiases.UserName
import com.jacksoncheek.userprofile.sample.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val graph get() = Graph.instance

    private val viewModelFactory by lazy {
        graph.viewModelFactory
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    private val layout: MainView by lazy {
        activity!!.findViewById(R.id.main_view) as MainView
    }

    private val layoutRes: Int = R.layout.sample_main_view

    private val ui: Ui = Ui()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.observe(viewModel.scope) { intention -> viewModel.send(intention) }

        viewModel.scope.launch {
            viewModel.states().consumeEach { viewState -> ui.render(viewState) }
        }
    }

    private inner class Ui : MainUi {

        private val delegate by lazy { layout }

        override fun observe(scope: CoroutineScope, listener: (MainViewModel.Intention) -> Unit) {
            delegate.observe(scope, listener)
        }

        override fun showUserName(userName: UserName) {
            delegate.showUserName(userName)
        }

        override fun showError(error: String) {
            delegate.showError(error)
        }

        override fun enableGetUserButton(enable: Boolean) {
            delegate.enableGetUserButton(enable)
        }
    }
}