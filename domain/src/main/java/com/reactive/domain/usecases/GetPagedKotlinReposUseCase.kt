package com.reactive.domain.usecases

import com.reactive.domain.repositories.GithubRepository
import javax.inject.Inject

class GetPagedKotlinReposUseCase @Inject constructor(
    private val repo : GithubRepository,
) {
    operator fun invoke() = repo.getKotlinRepos()
}
