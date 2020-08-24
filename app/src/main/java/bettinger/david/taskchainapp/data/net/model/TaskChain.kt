package bettinger.david.taskchainapp.data.net.model

import java.io.Serializable

data class TaskChain (
    val id: Int,
    val createdAt: String,
    val deadline: String,
    val name: String,
    val description: String,
    val completed: Boolean,
    val createdBy: User,
    val tasks: List<Task>
): Serializable


