package com.reactive.domain.usecases

import com.reactive.domain.repositories.GithubRepository
import javax.inject.Inject

class SearchReposUseCase @Inject constructor(
    private val repo : GithubRepository,
) {

    operator fun invoke(query : String) = repo.searchKotlinRepos(query)
}
