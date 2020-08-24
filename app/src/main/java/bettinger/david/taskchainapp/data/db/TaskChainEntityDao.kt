package bettinger.david.taskchainapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface TaskChainEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskChainEntity: TaskChainEntity): Completable

    @Query("SELECT * FROM task_chain")
    fun getAll(): Observable<List<TaskChainEntity>>

    @Query("SELECT * FROM task_chain WHERE id = :id LIMIT 1")
    fun getById(id: Int): Single<TaskChainEntity>

}