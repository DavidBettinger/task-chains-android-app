package bettinger.david.taskchainapp.viewmodel



import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.usecase.GetAllUsersUseCase
import bettinger.david.taskchainapp.usecase.GetCommentsUseCase
import bettinger.david.taskchainapp.usecase.GetTaskUseCase
import bettinger.david.taskchainapp.usecase.SaveCommentUseCase
import bettinger.david.taskchainapp.utils.Cache
import java.util.*

class EditTaskViewModel @ViewModelInject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _taskLiveData = MutableLiveData<TaskEntity>()
    val taskLiveData: LiveData<TaskEntity>
        get() = _taskLiveData


    private val _commentsLiveData = MutableLiveData<List<CommentEntity>>()
    val commentsLiveData: LiveData<List<CommentEntity>>
        get() = _commentsLiveData

    private val _messageLiveData = MutableLiveData<Int>()
    val messageLiveData: LiveData<Int>
        get() = _messageLiveData

    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean>
        get() = _isLoaded

    init {
        _isLoaded.value = false
    }

    fun saveCommentAndStatus(
        commentText: String,
        statusChanged: Boolean,
        newStatus: Boolean
    ): CommentEntity {
        if (statusChanged) {
            _taskLiveData.value!!.completed = newStatus
        }

        val commentEntity = CommentEntity(
            text = commentText,
            createdBy = Cache.currentUser!!,
            createdByUserId = Cache.currentUser!!.id,
            createdAt = Date.from(Calendar.getInstance().toInstant()).toString(),
            taskEntityId = _taskLiveData.value!!.id
        )

        saveCommentUseCase.saveComment(commentEntity)
        saveCommentUseCase.execute(
            onSuccess = {
                _messageLiveData.value = R.string.comment_saved
            },
            onError = {
                it.printStackTrace()
            }
        )

        return commentEntity
    }

    fun loadTaskEntity(taskId: Int) {
        getTaskUseCase.setTaskId(taskId)
        getTaskUseCase.execute(
            onSuccess = {
                _taskLiveData.value = it
                loadComments(taskId)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    private fun loadComments(taskId: Int) {
        getCommentsUseCase.setTaskId(taskId)
        getCommentsUseCase.execute(
            onNext = {
                _commentsLiveData.value = it
                _isLoaded.value = true
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}