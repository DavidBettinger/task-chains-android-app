package bettinger.david.taskchainapp.data.mapper

import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.net.model.User
import bettinger.david.taskchainapp.utils.EntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor(): EntityMapper<UserEntity, User> {

    override fun mapFromEntity(entity: UserEntity): User {
        return User(id = entity.id,
            name = entity.name,
            firstName = entity.firstName,
            userName = entity.userName,
            role = entity.role)
    }

    override fun mapToEntity(domainModel: User): UserEntity {
        return UserEntity(id = domainModel.id,
            name = domainModel.name,
            firstName = domainModel.firstName,
            userName = domainModel.userName,
            role = domainModel.role)
    }
}