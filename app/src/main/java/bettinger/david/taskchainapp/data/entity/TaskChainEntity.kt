package bettinger.david.taskchainapp.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "task_chain")
data class TaskChainEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var createdAt: String,
    var deadline: String,
    var name: String,
    var description: String,
    var completed: Boolean,
    var createdByUserId: Int,
    var taskCount: Int,
    var numberOfCompletedTasks: Int,
    @Ignore
    var createdByUser: UserEntity = UserEntity(-1),
    @Ignore
    val tasks: List<TaskEntity> = mutableListOf()
) {

    constructor(): this(-1, "", "", "", "", false, -1, 0, 0)

}


