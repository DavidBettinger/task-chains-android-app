package bettinger.david.taskchainapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bettinger.david.taskchainapp.data.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity): Completable

    @Query("SELECT * FROM user")
    fun get(): Single<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun getById(id: Int): Single<UserEntity>

    @Query("SELECT * FROM user WHERE isCurrentUser LIMIT 1")
    fun getCurrentUser(): Single<UserEntity>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE id = :id)")
    fun userExist(id : Int) : Single<Boolean>

}