package com.reactive.data.remote.model.mappers

import com.reactive.data.remote.model.RepoDto
import com.reactive.data.remote.model.ResultListDto
import com.reactive.domain.model.Repo
import com.reactive.domain.model.ResultList
import com.reactive.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoResultListDtoMapper @Inject constructor(
    private val repoDtoMapper : RepoDtoMapper,
): DomainMapper<ResultListDto<RepoDto>, ResultList<Repo>> {
    override fun mapToDomainModel(model : ResultListDto<RepoDto>) : ResultList<Repo> {
        return ResultList(
            totalCount = model.totalCount,
            items = model.items.map { repoDtoMapper.mapToDomainModel(it) }
        )
    }

    override fun mapFromDomainModel(domainModel : ResultList<Repo>) : ResultListDto<RepoDto> =
        ResultListDto(
            totalCount = domainModel.totalCount,
            items = domainModel.items.map { repoDtoMapper.mapFromDomainModel(it) }
        )
}
