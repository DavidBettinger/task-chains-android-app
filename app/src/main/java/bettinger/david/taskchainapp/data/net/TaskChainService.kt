package bettinger.david.taskchainapp.data.net

import bettinger.david.taskchainapp.data.net.model.TaskChain
import io.reactivex.rxjava3.core.Observable



import retrofit2.http.GET

interface TaskChainService {

    @GET("all")
    fun getAllTaskChains(): Observable<List<TaskChain>>
}