package bettinger.david.taskchainapp.data.mapper

import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.net.model.Comment
import bettinger.david.taskchainapp.data.net.model.Task
import bettinger.david.taskchainapp.utils.EntityMapper
import javax.inject.Inject

class TaskMapper @Inject constructor(private val commentMapper: CommentMapper): EntityMapper<TaskEntity, Task> {

    override fun mapFromEntity(entity: TaskEntity): Task {
        return Task(id = entity.id,
            createdAt = entity.createdAt,
            comments = commentMapper.mapFromEntityList(entity.comments) as MutableList<Comment>,
            completed = entity.completed,
            deadline = entity.deadline,
            description = entity.description,
            name = entity.name,
            taskNumber = entity.taskNumber)
    }

    override fun mapToEntity(domainModel: Task): TaskEntity {
        return TaskEntity(id = domainModel.id,
            createdAt = domainModel.createdAt,
            comments = commentMapper.mapToEntityList(domainModel.comments, domainModel.id) as MutableList<CommentEntity>,
            completed = domainModel.completed,
            deadline = domainModel.deadline,
            description = domainModel.description,
            name = domainModel.name,
            taskNumber = domainModel.taskNumber,
            taskChainId = 0)
    }

    fun mapToEntityList(domainModels: List<Task>, chainId: Int): List<TaskEntity> {
        return domainModels.map { mapToEntity(it).apply { taskChainId = chainId } }
    }

    fun mapFromEntityList(entities: List<TaskEntity>): List<Task> {
        return entities.map { mapFromEntity(it) }
    }
}