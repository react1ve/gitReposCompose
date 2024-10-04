package com.reactive.composerepos.ui.navigation

import com.reactive.domain.model.Repo

sealed class Screen(val route : String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Details: Screen("repo/{owner}/{name}") {
        fun createRoute(repo : Repo) = "repo/${repo.owner.login}/${repo.name}"
    }
}
