package bettinger.david.taskchainapp.di

import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.*
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import bettinger.david.taskchainapp.usecase.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun getAllUsersUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetAllUsersUseCase {
        return GetAllUsersUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getCommentsUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetCommentsUseCase {
        return GetCommentsUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getTaskChainsUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetTaskChainsUseCase {
        return GetTaskChainsUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getTaskChainUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetTaskChainUseCase {
        return GetTaskChainUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getTasksUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetTasksUseCase {
        return GetTasksUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getTaskUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetTaskUseCase {
        return GetTaskUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getUserUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun getLoginUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): LoginUseCase {
        return LoginUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun providesScheduler(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Singleton
    @Provides
    fun providesSaveCommentUseCase(taskChainRepository: TaskChainRepository, schedulerProvider: BaseSchedulerProvider): SaveCommentUseCase {
        return SaveCommentUseCase(taskChainRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun providesRefreshCacheUseCase(taskChainRepository: TaskChainRepository): RefreshCacheUseCase {
        return RefreshCacheUseCase(taskChainRepository)
    }
}