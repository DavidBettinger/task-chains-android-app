package bettinger.david.taskchainapp.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.usecase.GetCurrentUserUseCase
import bettinger.david.taskchainapp.usecase.LoginUseCase
import bettinger.david.taskchainapp.utils.Cache
import bettinger.david.taskchainapp.utils.NetworkChecker

class LoginViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase,
    private val networkChecker: NetworkChecker,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //TODO check savedStateHandle to see if user is already logged in

    companion object {
        private const val LOGGED_IN_USER_KEY = "Current user"
    }

    private val _userLiveData = MutableLiveData<UserEntity>()
    val userLiveData: LiveData<UserEntity>
        get() = _userLiveData


    private val _messageLiveData = MutableLiveData<Int>()
    val messageLiveData: LiveData<Int>
        get() = _messageLiveData


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading



    init {
        _isLoading.value = false
        //TODO add option for auto login
        if (true) {
            loadCurrentUser()
        }
    }

    private fun loadCurrentUser() {
        getCurrentUserUseCase.execute(
            onSuccess = {
                _userLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    //TODO create correct login
    fun loginUserWithUserName(userName: String) {

        if (networkChecker.isNetworkAvailable()) {
            _isLoading.value = true
            loginUseCase.setUserName(userName)
            loginUseCase.execute(
                onSuccess = {
                    _isLoading.value = false
                    _userLiveData.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
        } else {
            _messageLiveData.value = R.string.please_enable_network_to_login
        }

    }

}