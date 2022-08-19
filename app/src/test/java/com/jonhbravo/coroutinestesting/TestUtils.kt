package com.jonhbravo.coroutinestesting

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.ContinuationInterceptor

@OptIn(ExperimentalCoroutinesApi::class)
fun TestScope.getTestDispatcher() =
    this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher