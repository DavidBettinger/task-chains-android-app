package bettinger.david.taskchainapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bettinger.david.taskchainapp.data.entity.TaskEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface TaskEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(taskEntity: List<TaskEntity>): Completable

    @Query("SELECT * FROM task")
    fun get(): Observable<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1")
    fun getById(id: Int): Single<TaskEntity>

    //TODO sort by taskNumber
    @Query("SELECT * FROM task WHERE taskChainId = :id")
    fun getByTaskChainId(id: Int): Observable<List<TaskEntity>>

}