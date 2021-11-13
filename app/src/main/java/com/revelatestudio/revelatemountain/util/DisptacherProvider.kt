package com.revelatestudio.revelatemountain.util

import kotlinx.coroutines.CoroutineDispatcher

interface DisptacherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

}