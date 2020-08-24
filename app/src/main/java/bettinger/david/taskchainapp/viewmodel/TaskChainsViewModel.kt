package bettinger.david.taskchainapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.data.repository.TaskChainRepositoryImpl
import bettinger.david.taskchainapp.usecase.GetTaskChainsUseCase
import bettinger.david.taskchainapp.usecase.RefreshCacheUseCase
import bettinger.david.taskchainapp.utils.NetworkChecker

class TaskChainsViewModel @ViewModelInject constructor(
    private val getTaskChainsUseCase: GetTaskChainsUseCase,
    private val networkChecker: NetworkChecker,
    private val refreshCacheUseCase: RefreshCacheUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskChainsLiveData = MutableLiveData<List<TaskChainEntity>>()
    val taskChainsLiveData: LiveData<List<TaskChainEntity>>
        get() = _taskChainsLiveData

    private val _messageLiveData = MutableLiveData<Int>()
    val messageLiveData: LiveData<Int>
        get() = _messageLiveData

    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean>
        get() = _isLoaded

    init {
        _isLoaded.value = false
    }


    fun loadTaskChains() {
        if (networkChecker.isNetworkAvailable()){
            refreshCacheUseCase.execute()
        } else {
            _messageLiveData.value = R.string.no_internet
        }
        getTaskChainsUseCase.execute(
            onNext = {
                _isLoaded.value = true
                _taskChainsLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}