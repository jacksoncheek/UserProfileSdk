package com.jacksoncheek.userprofile.sample

/**
 * Override these extension functions to add different
 * functionality to the `CONFIGURABLE` flavor dimension.
 */
fun MainApplication.onCreateConfigurableFlavorDimension(): ConfigurableGraph {

    // Initialize and return the Dev DI object graph
    return ConfigurableGraph.create(this)
}