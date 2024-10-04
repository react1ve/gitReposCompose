package com.reactive.composerepos.ui.extensions

import com.reactive.composerepos.ui.screens.ScreenStatus
import com.reactive.domain.model.NoInternetConnectionException

fun Throwable.getScreenStatusDependingOnError() : ScreenStatus {
    return when (this) {
        is NoInternetConnectionException -> ScreenStatus.NO_INTERNET
        else -> ScreenStatus.ERROR
    }
}
