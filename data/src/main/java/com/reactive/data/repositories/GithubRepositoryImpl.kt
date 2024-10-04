package com.reactive.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.kittinunf.result.Result
import com.reactive.core.di.IoDispatcher
import com.reactive.data.Constants
import com.reactive.data.local.db.RepoDatabase
import com.reactive.data.local.mappers.RepoDboMapper
import com.reactive.data.paging.ReposRemoteMediator
import com.reactive.data.paging.SearchPagingSource
import com.reactive.data.remote.datasource.GithubDataSource
import com.reactive.domain.model.Repo
import com.reactive.domain.repositories.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubDatasource : GithubDataSource,
    @IoDispatcher private val dispatcher : CoroutineDispatcher,
    private val db : RepoDatabase,
    private val dboMapper : RepoDboMapper,
): GithubRepository {

    override fun getKotlinRepos() : Flow<PagingData<Repo>> {
        val pagingSourceFactory = { db.repoDao().getAll() }
        return Pager(
            config = getPagingConfig(),
            remoteMediator = ReposRemoteMediator(
                dataSource = githubDatasource,
                database = db,
                mapper = dboMapper,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { page ->
            page.map { dboMapper.mapToDomainModel(it) }
        }
    }

    override fun searchKotlinRepos(query : String) : Flow<PagingData<Repo>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                SearchPagingSource(dataSource = githubDatasource, query = query)
            }
        ).flow
    }

    private fun getPagingConfig() = PagingConfig(
        pageSize = Constants.ELEMENTS_PER_PAGE,
        prefetchDistance = Constants.ELEMENTS_PER_PAGE / 2,
        initialLoadSize = Constants.ELEMENTS_PER_PAGE
    )

    override suspend fun getKotlinRepo(owner : String, name : String) : Result<Repo, Exception> =
        withContext(dispatcher) {
            Result.of {
                githubDatasource.getRepository(owner, name)
            }
        }
}
