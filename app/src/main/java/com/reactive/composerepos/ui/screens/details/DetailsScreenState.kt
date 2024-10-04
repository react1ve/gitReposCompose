package com.reactive.composerepos.ui.screens.details

import com.reactive.composerepos.ui.screens.ScreenStatus
import com.reactive.domain.model.Repo

data class DetailsScreenState(
    val repo : Repo? = null,
    val screenStatus : ScreenStatus = ScreenStatus.LOADING,
) {
    companion object {
        val Empty = DetailsScreenState(repo = null, screenStatus = ScreenStatus.LOADING)
    }
}
