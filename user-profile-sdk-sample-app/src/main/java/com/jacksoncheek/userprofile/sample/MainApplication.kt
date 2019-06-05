package com.jacksoncheek.userprofile.sample

import android.app.Application
import kotlin.LazyThreadSafetyMode.NONE

class MainApplication : Application() {

    private lateinit var configurableGraph: ConfigurableGraph

    val graph: Graph by lazy(NONE) {
        Graph.Builder()
            .userProfileSdk(configurableGraph.userProfileSdk)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        // Must be initialized before the common graph for access
        // to the UserProfileSdk in the builder
        configurableGraph = onCreateConfigurableFlavorDimension()

        // Initialize the common graph before onCreate() finishes
        graph
    }
}