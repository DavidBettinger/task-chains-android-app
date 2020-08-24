package bettinger.david.taskchainapp.data.mapper

import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.net.model.Task
import bettinger.david.taskchainapp.data.net.model.TaskChain
import bettinger.david.taskchainapp.utils.EntityMapper
import javax.inject.Inject

class TaskChainMapper @Inject constructor(private val taskMapper: TaskMapper, private val userMapper: UserMapper) :
    EntityMapper<TaskChainEntity, TaskChain> {

    override fun mapFromEntity(entity: TaskChainEntity): TaskChain {
        return TaskChain(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            deadline = entity.deadline,
            completed = entity.completed,
            createdAt = entity.createdAt,
            createdBy = userMapper.mapFromEntity(entity.createdByUser),
            tasks = taskMapper.mapFromEntityList(entity.tasks)
        )
    }

    override fun mapToEntity(domainModel: TaskChain): TaskChainEntity {
        return TaskChainEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            deadline = domainModel.deadline,
            completed = domainModel.completed,
            createdAt = domainModel.createdAt,
            createdByUser = userMapper.mapToEntity(domainModel.createdBy),
            tasks = taskMapper.mapToEntityList(domainModel.tasks, domainModel.id),
            createdByUserId = domainModel.createdBy.id
        )
    }

    fun mapToEntityList(domainModels: List<TaskChain>): List<TaskChainEntity> {
        return domainModels.map { mapToEntity(it) }
    }

    fun mapFromEntityList(entities: List<TaskChainEntity>): List<TaskChain> {
        return entities.map { mapFromEntity(it) }
    }

}