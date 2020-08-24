package bettinger.david.taskchainapp.di

import android.content.Context
import androidx.room.Room
import bettinger.david.taskchainapp.data.db.TaskChainDatabase
import bettinger.david.taskchainapp.data.mapper.CommentMapper
import bettinger.david.taskchainapp.data.mapper.TaskChainMapper
import bettinger.david.taskchainapp.data.mapper.TaskMapper
import bettinger.david.taskchainapp.data.mapper.UserMapper
import bettinger.david.taskchainapp.data.net.RetrofitClient
import bettinger.david.taskchainapp.data.net.TaskChainService
import bettinger.david.taskchainapp.data.net.UserService
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.data.repository.TaskChainRepositoryImpl
import bettinger.david.taskchainapp.usecase.*
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun getCommentMapper(userMapper: UserMapper): CommentMapper {
        return CommentMapper(userMapper)
    }

    @Singleton
    @Provides
    fun getUserMapper(): UserMapper {
        return UserMapper()
    }

    @Singleton
    @Provides
    fun getTaskMapper(commentMapper: CommentMapper): TaskMapper {
        return TaskMapper(commentMapper)
    }

    @Singleton
    @Provides
    fun getTaskChainMapper(taskMapper: TaskMapper, userMapper: UserMapper): TaskChainMapper {
        return TaskChainMapper(taskMapper, userMapper)
    }

    @Singleton
    @Provides
    fun getTaskChainDatabase(@ApplicationContext context: Context): TaskChainDatabase {
        return Room
            .databaseBuilder(
                context,
                TaskChainDatabase::class.java,
                TaskChainDatabase.DB_NAME
            ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Singleton
    @Provides
    fun getTaskChainService(retrofitClient: RetrofitClient): TaskChainService {
        return retrofitClient.getTaskChainService()
    }

    @Singleton
    @Provides
    fun getUserService(retrofitClient: RetrofitClient): UserService {
        return retrofitClient.getUserService()
    }

    @Singleton
    @Provides
    fun getTaskChainRepository(
        taskChainDatabase: TaskChainDatabase,
        taskChainService: TaskChainService,
        userService: UserService,
        userMapper: UserMapper,
        schedulerProvider: BaseSchedulerProvider,
        taskChainMapper: TaskChainMapper
    ): TaskChainRepository {
        return TaskChainRepositoryImpl(
            taskChainDatabase,
            taskChainService,
            userService,
            userMapper,
            schedulerProvider,
            taskChainMapper
        )
    }



}