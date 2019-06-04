package com.jacksoncheek.userprofile.ui.view.user

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jacksoncheek.userprofile.core.data.model.User
import com.jacksoncheek.userprofile.ui.R
import com.jacksoncheek.userprofile.ui.UserProfileSdkUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class UserProfileActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        UserProfileSdkUi.instance.viewModelFactory
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(UserProfileViewModel::class.java)
    }

    private val ui: Ui by lazy {
        Ui(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_profile)

        ui.observe(viewModel.scope) { intention -> viewModel.send(intention) }

        viewModel.scope.launch {
            viewModel.states().consumeEach { viewState -> ui.render(viewState) }
        }

        viewModel.send(
            UserProfileViewModel.Intention.StoreActivity(
                activity = this
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.scope.coroutineContext[Job]!!.cancel()
    }

    private inner class Ui(private val activity: UserProfileActivity) : UserUi {

        private val userImage = activity.findViewById<ImageView>(R.id.user_image)

        private val userImageHolder = activity.findViewById<RelativeLayout>(R.id.user_image_holder)

        private val userName = activity.findViewById<TextView>(R.id.user_name)

        private val userGender = activity.findViewById<TextView>(R.id.user_gender)

        private val userCountry = activity.findViewById<TextView>(R.id.user_country)

        private val userPhone = activity.findViewById<TextView>(R.id.user_phone)

        private val userEmail = activity.findViewById<TextView>(R.id.user_email)

        private val userDob = activity.findViewById<TextView>(R.id.user_dob)

        private val gotItButton = activity.findViewById<Button>(R.id.user_got_it_button)

        private val progressBar = activity.findViewById<ProgressBar>(R.id.user_progress_bar)

        override fun observe(scope: CoroutineScope, listener: (UserProfileViewModel.Intention) -> Unit) {
            setUpListeners()
            this.listener = listener

            scope.coroutineContext[Job]!!.invokeOnCompletion {
                removeListeners()
                this.listener = null
            }
        }

        override fun showLoading(show: Boolean) {
            val loadingVisibility = if (show) View.VISIBLE else View.INVISIBLE
            val screenVisibility = if (show) View.INVISIBLE else View.VISIBLE

            progressBar.visibility = loadingVisibility

            userImage.visibility = screenVisibility
            userImageHolder.visibility = screenVisibility
            userName.visibility = screenVisibility
            userGender.visibility = screenVisibility
            userCountry.visibility = screenVisibility
            userPhone.visibility = screenVisibility
            userDob.visibility = screenVisibility
        }

        @SuppressLint("SetTextI18n")
        override fun showUserProfile(show: Boolean, user: User?) {
            if (show && user != null) {
                val placeholder = CircularProgressDrawable(activity)
                placeholder.strokeWidth = 5f
                placeholder.centerRadius = 30f
                placeholder.start()

                Glide.with(activity)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(placeholder)
                    .into(userImage)

                userName.text = "${user.name} ${user.surname}"
                userGender.text = user.gender
                userCountry.text = user.region
                userPhone.text = user.phone
                userEmail.text = user.email
                userDob.text = user.birthday.mdy
            }
        }

        override fun enableOkButton(enable: Boolean) {
            gotItButton.isEnabled = enable
        }

        private var listener: ((UserProfileViewModel.Intention) -> Unit)? = null

        @Suppress("NOTHING_TO_INLINE")
        private inline fun notify(intention: UserProfileViewModel.Intention) {
            listener?.invoke(intention)
        }

        private fun setUpListeners() {
            gotItButton.setOnClickListener {
                notify(UserProfileViewModel.Intention.UserClickedOk)
            }
        }

        private fun removeListeners() {
            gotItButton.setOnClickListener(null)
        }
    }

    /**
     * Arguments for launching a [UserProfileActivity].
     */
    internal class Args {

        fun launch(context: Context) {
            val intent = Intent(context, UserProfileActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

            context.startActivity(intent)
        }
    }
}