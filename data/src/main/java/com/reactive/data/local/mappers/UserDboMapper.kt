package com.reactive.data.local.mappers

import com.reactive.data.local.model.UserDbo
import com.reactive.domain.model.User
import com.reactive.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDboMapper @Inject constructor(): DomainMapper<UserDbo, User> {
    override fun mapToDomainModel(model : UserDbo) : User {
        return User(
            login = model.login,
            id = model.userId,
            avatarUrl = model.avatarUrl
        )
    }

    override fun mapFromDomainModel(domainModel : User) : UserDbo {
        return UserDbo(
            login = domainModel.login,
            userId = domainModel.id,
            avatarUrl = domainModel.avatarUrl
        )
    }
}
