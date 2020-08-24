package bettinger.david.taskchainapp.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

import java.util.*

@Entity(tableName = "comment")
data class CommentEntity(

    var text: String,
    var createdByUserId: Int,
    var taskEntityId: Int,
    var createdAt: String,
    @Ignore
    var createdBy: UserEntity = UserEntity(-1),
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString()
){
    constructor(): this("", 0, 0, "")
}