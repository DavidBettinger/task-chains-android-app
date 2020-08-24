package bettinger.david.taskchainapp.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.entity.UserEntity

@Database(
    entities = [UserEntity::class, CommentEntity::class, TaskChainEntity::class, TaskEntity::class],
    version = 2
)
//@TypeConverters(DateConverter::class) //TODO change the dates from string to instance and add converter
abstract class TaskChainDatabase : RoomDatabase() {

    abstract fun userEntityDao(): UserEntityDao
    abstract fun commentEntityDao(): CommentEntityDao
    abstract fun taskEntityDao(): TaskEntityDao
    abstract fun taskChainEntityDao(): TaskChainEntityDao

    companion object {
        const val DB_NAME = "Database"
    }
}