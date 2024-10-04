package com.reactive.composerepos.ui.screens

enum class ScreenStatus {
    SUCCESS, LOADING, NO_INTERNET, ERROR;

    fun isLoading() : Boolean = this == LOADING
}
