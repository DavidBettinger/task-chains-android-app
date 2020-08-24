package bettinger.david.taskchainapp.data.net.model

import java.io.Serializable

data class User (
    val id: Int,
    val name: String = "",
    val firstName: String = "",
    val userName: String = "",
    val role: String = ""
): Serializable

