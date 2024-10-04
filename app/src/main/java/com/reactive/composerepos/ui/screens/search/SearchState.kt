package com.reactive.composerepos.ui.screens.search

data class SearchState(
    val query : String = "",
    val isDebouncing : Boolean = false,
)
