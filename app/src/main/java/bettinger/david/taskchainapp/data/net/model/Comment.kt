package bettinger.david.taskchainapp.data.net.model

import java.io.Serializable

data class Comment (
    val id: String,
    val text: String,
    val createdBy: User,
    val createdAt: String
): Serializable