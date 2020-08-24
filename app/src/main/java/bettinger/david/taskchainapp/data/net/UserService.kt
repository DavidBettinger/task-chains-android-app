package bettinger.david.taskchainapp.data.net

import bettinger.david.taskchainapp.data.net.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface UserService {

    //TODO implement correct login method when API is finished
    @GET("login/{userName}")
    fun login(@Path("userName") userName: String): Single<User>

}