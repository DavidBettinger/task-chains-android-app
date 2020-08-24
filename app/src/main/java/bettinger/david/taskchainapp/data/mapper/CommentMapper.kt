package bettinger.david.taskchainapp.data.mapper

import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.net.model.Comment
import bettinger.david.taskchainapp.utils.EntityMapper
import javax.inject.Inject

class CommentMapper @Inject constructor(private val userMapper: UserMapper): EntityMapper<CommentEntity, Comment> {

    override fun mapFromEntity(entity: CommentEntity): Comment {
        return Comment(id = entity.id,
                createdAt = entity.createdAt,
                createdBy = userMapper.mapFromEntity(entity.createdBy),
                text = entity.text)
    }

    override fun mapToEntity(domainModel: Comment): CommentEntity {
        return CommentEntity(
            id = domainModel.id,
            createdAt = domainModel.createdAt,
            createdBy = userMapper.mapToEntity(domainModel.createdBy),
            text = domainModel.text,
            createdByUserId = domainModel.createdBy.id,
            taskEntityId = 0)
    }

    fun mapToEntityList(domainModels: List<Comment>, taskId: Int): List<CommentEntity> {
        return domainModels.map { mapToEntity(it).apply { taskEntityId = taskId } }
    }

    fun mapFromEntityList(entities: List<CommentEntity>): List<Comment> {
        return entities.map { mapFromEntity(it) }
    }
}