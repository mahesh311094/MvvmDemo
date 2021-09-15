package com.qstest.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineDispatchersProvider {
    val main: CoroutineContext by lazy { Dispatchers.Main }
    val io: CoroutineContext by lazy { Dispatchers.IO }
    val default: CoroutineContext by lazy { Dispatchers.Default }
}