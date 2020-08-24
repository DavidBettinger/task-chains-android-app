package bettinger.david.taskchainapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.usecase.GetTaskChainUseCase
import bettinger.david.taskchainapp.usecase.GetTasksUseCase

class TasksViewModel @ViewModelInject constructor(
    private val getTaskChainUseCase: GetTaskChainUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskChainLiveData = MutableLiveData<TaskChainEntity>()
    val taskChainLiveData: LiveData<TaskChainEntity>
        get() = _taskChainLiveData


    private val _tasksLiveData = MutableLiveData<List<TaskEntity>>()
    val tasksLiveData: LiveData<List<TaskEntity>>
        get() = _tasksLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean>
        get() = _isLoaded

    init {
        _isLoaded.value = false
    }

    fun loadTaskChainWithTasks(taskChainId: Int) {
        getTaskChainUseCase.setTaskChainId(taskChainId)
        getTaskChainUseCase.execute(
            onSuccess = {
                loadTaskChainTasks(taskChainId)
                _taskChainLiveData.value = it
            },
            onError = {
                it.printStackTrace()
                _messageLiveData.value = it.message
            }

        )
    }


    private fun loadTaskChainTasks(taskChainId: Int){
        getTasksUseCase.setTaskChainId(taskChainId)
        getTasksUseCase.execute(
            onNext = {
                _tasksLiveData.value = it
                _isLoaded.value = true
            },
            onError = {
                it.printStackTrace()
                _messageLiveData.value = it.message
            }
        )
    }

}