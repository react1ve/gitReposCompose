package com.reactive.data.remote.datasource

import com.reactive.data.Constants
import com.reactive.data.remote.RepositoryCache
import com.reactive.data.remote.model.mappers.RepoDtoMapper
import com.reactive.data.remote.model.mappers.RepoResultListDtoMapper
import com.reactive.data.remote.services.SearchServiceApi
import com.reactive.domain.model.NoInternetConnectionException
import com.reactive.domain.model.Repo
import com.reactive.domain.model.ResultList
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GithubDataSource @Inject constructor(
    private val service : SearchServiceApi,
    private val repoDtoMapper : RepoDtoMapper,
    private val resultDtoMapper : RepoResultListDtoMapper,
    private val cache : RepositoryCache,
) {

    suspend fun searchRepositories(
        page : Int,
        query : String = "",
    ) : ResultList<Repo> {
        return call {
            val result = service.searchRepositories(
                query = getKotlinQuery(query),
                page = page,
                sortBy = "stars"
            )
            val mappedResult = resultDtoMapper.mapToDomainModel(result)
            cache.saveRepos(mappedResult.items)
            mappedResult
        }
    }

    suspend fun getRepository(owner : String, name : String) : Repo {
        val cached = cache.getRepo("$owner/$name")
        return cached ?: call { repoDtoMapper.mapToDomainModel(service.getRepository(owner, name)) }
    }

    fun isLastPage(currentPage : Int, totalCount : Int) =
        Constants.ELEMENTS_PER_PAGE * (currentPage + 1) > totalCount

    @Suppress("TooGenericExceptionCaught")
    private suspend fun <T> call(call : suspend () -> T) : T {
        return try {
            call.invoke()
        } catch (e : Exception) {
            when (e) {
                is ConnectException, is SocketTimeoutException, is UnknownHostException ->
                    throw NoInternetConnectionException()

                else -> throw e
            }
        }
    }

    private fun getKotlinQuery(query : String) : String {
        return "$query${if (query.isNotEmpty()) "+" else ""}language:kotlin"
    }
}
