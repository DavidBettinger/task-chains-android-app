package bettinger.david.taskchainapp.data.net.model

import java.io.Serializable

data class Task (
    val id: Int,
    val name: String,
    val description: String,
    val completed: Boolean,
    val taskNumber: Int,
    val createdAt: String,
    val deadline: String,
    val comments: MutableList<Comment>
): Serializable
