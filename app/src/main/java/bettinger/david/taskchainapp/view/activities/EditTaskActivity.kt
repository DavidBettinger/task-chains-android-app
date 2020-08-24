package bettinger.david.taskchainapp.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.utils.Constants
import bettinger.david.taskchainapp.view.adapters.CommentAdapter
import bettinger.david.taskchainapp.viewmodel.EditTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_task.*


@AndroidEntryPoint
class EditTaskActivity : BaseActivity() {

    private var taskEntity: TaskEntity? = null

    private val viewModel: EditTaskViewModel by viewModels()


    private lateinit var commentAdapter: CommentAdapter

    private var statusChanged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        var taskId: Int = -1

        if (intent.hasExtra(TasksActivity.TASK_ID)) {
            taskId = intent.getIntExtra(TasksActivity.TASK_ID, -1)
        }

        loadTaskAndComments(taskId)
    }

    private fun loadTaskAndComments(taskId: Int) {

        viewModel.isLoaded.observe(this, {
            it?.let { visibility ->
                if (visibility) hideProgressDialog() else showCustomProgressDialog()
            }
        })

        viewModel.loadTaskEntity(taskId)

        if (taskId >= 0) {
            viewModel.taskLiveData.observe(this, {
                taskEntity = it
                setUpUI()
            })
        } else {
            showMessage(resources.getString(R.string.load_task_error), this)
        }
    }

    private fun setUpUI() {
        setSupportActionBar(toolbar_edit_task)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = taskEntity!!.name

        toolbar_edit_task.setNavigationOnClickListener {
            onBackPressed()
        }
        tv_edit_activity_description.text = taskEntity!!.description
        tv_edit_task_deadline.text = Constants.formatDate(taskEntity!!.deadline)

        //TODO save completed state when changed
        switch_completed.isChecked = taskEntity!!.completed

        viewModel.commentsLiveData.observe(this, { comments ->
            if (comments.isNotEmpty()) {
                rv_comment_list.visibility = View.VISIBLE
                setupCommentRecyclerView(comments)
            }
        })


        switch_completed.setOnCheckedChangeListener { _, b ->
            statusChanged = when(statusChanged){
                true -> false
                false -> true
            }
            Log.d("Status", "Status changed. New status: $b")
        }

        btn_save_comment.setOnClickListener {
            saveCommentAndStatus()
        }

    }

    private fun saveCommentAndStatus() {
        viewModel.saveCommentAndStatus(et_comment.text.toString(), statusChanged, switch_completed.isChecked)

        viewModel.messageLiveData.observe(this, {
            showMessage(resources.getString(it), this)
        })
        et_comment.setText("")
    }


    private fun setupCommentRecyclerView(commentList: List<CommentEntity>) {

        rv_comment_list.layoutManager = LinearLayoutManager(this)
        rv_comment_list.setHasFixedSize(true)
        commentAdapter = CommentAdapter(this, commentList as MutableList<CommentEntity>)
        rv_comment_list.adapter = commentAdapter

    }


    fun formatDate(date: String): String {
        //TODO Format date
        return date.substring(0..9)

    }
}