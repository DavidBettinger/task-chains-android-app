package bettinger.david.taskchainapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface CommentEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commentEntity: CommentEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(taskEntity: List<CommentEntity>): Completable

    @Query("SELECT * FROM comment")
    fun get(): Observable<List<CommentEntity>>

    @Query("SELECT * FROM comment WHERE id = :id LIMIT 1")
    fun getById(id: String): Single<CommentEntity>

    //TODO sort by timestamp (implement timestamp)
    @Query("SELECT * FROM comment WHERE taskEntityId = :id")
    fun getByTaskId(id: Int): Observable<List<CommentEntity>>

}