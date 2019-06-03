package com.jacksoncheek.userprofile.common.internal.logic

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Abstraction on threading using Kotlin's [CoroutineContext]
 */
class UserProfileDispatchers(
    val ui: CoroutineContext = Dispatchers.Main,
    val background: CoroutineContext = Dispatchers.Default,
    val io: CoroutineContext = Dispatchers.IO
)