package bettinger.david.taskchainapp.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "task")
data class TaskEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var description: String,
    var completed: Boolean,
    var taskNumber: Int,
    var createdAt: String,
    var taskChainId: Int,
    var deadline: String,
    @Ignore
    val comments: MutableList<CommentEntity> = mutableListOf()
){
    constructor(): this(-1, "", "", false, -1, "", -1, "")
}
