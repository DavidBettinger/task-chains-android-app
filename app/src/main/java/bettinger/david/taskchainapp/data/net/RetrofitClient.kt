package bettinger.david.taskchainapp.data.net

import bettinger.david.taskchainapp.data.net.model.TaskChain
import bettinger.david.taskchainapp.data.net.model.User
import bettinger.david.taskchainapp.utils.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient


import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitClient  @Inject constructor() {

    private val taskChainService: TaskChainService
    private val userService: UserService


    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        taskChainService = retrofit.create(TaskChainService::class.java)
        userService = retrofit.create(UserService::class.java)
    }

    fun getTaskChains(): Observable<List<TaskChain>>{
        return taskChainService.getAllTaskChains()
    }

    //TODO implement correct login method when API is finished
    fun login(userName: String): Single<User>?{
        return userService.login(userName)
    }

    fun getTaskChainService(): TaskChainService{
        return taskChainService
    }

    fun getUserService(): UserService{
        return userService
    }
}