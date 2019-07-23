package com.jacksoncheek.userprofile.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.content,
                MainFragment()
            )
            .commit()
    }
}