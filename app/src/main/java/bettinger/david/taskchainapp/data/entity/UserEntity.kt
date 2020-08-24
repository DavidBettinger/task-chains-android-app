package bettinger.david.taskchainapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String = "",
    val firstName: String = "",
    val userName: String = "",
    val role: String = "",
    var isCurrentUser: Boolean = false
)

