package bettinger.david.taskchainapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels

import androidx.lifecycle.Observer

import androidx.recyclerview.widget.LinearLayoutManager
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.view.adapters.TasksAdapter
import bettinger.david.taskchainapp.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_tasks.*

@AndroidEntryPoint
class TasksActivity : BaseActivity() {

    companion object {
        const val TASK_ID = "Task id"

    }

    private val viewModel: TasksViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        var taskChainId = -1
        if (intent.hasExtra(TaskChainsActivity.TASK_CHAIN_ID)) {
            taskChainId = intent.getIntExtra(TaskChainsActivity.TASK_CHAIN_ID, -1)
        }
        loadTaskChain(taskChainId)

    }

    private fun loadTaskChain(taskChainId: Int) {
        if (taskChainId >= 0) {
            viewModel.isLoaded.observe(this, {
                it?.let { visibility ->
                    if (visibility) hideProgressDialog() else showCustomProgressDialog()
                }
            })
            viewModel.loadTaskChainWithTasks(taskChainId)
            viewModel.taskChainLiveData.observe(this, { taskChainEntity ->
                hideProgressDialog()
                setUpUI(taskChainEntity.name)
                setupTasksRecyclerView()
            })
        } else {
            showMessage(getString(R.string.load_task_chain_error), this)
        }
    }

    private fun setUpUI(taskChainName: String) {
        setSupportActionBar(toolbar_tasks)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = taskChainName

        viewModel.messageLiveData.observe(this, {
            showMessage(it, this)
        })

        toolbar_tasks.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun setupTasksRecyclerView() {

        rv_task_list.layoutManager = LinearLayoutManager(this)
        rv_task_list.setHasFixedSize(true)

        viewModel.tasksLiveData.observe(this, { taskEntityList ->
            val tasksAdapter = TasksAdapter(this, taskEntityList)
            rv_task_list.adapter = tasksAdapter

            tasksAdapter.setOnClickListener(object :
                TasksAdapter.OnClickListener {
                override fun onClick(position: Int, taskEntity: TaskEntity) {
                    val intent = Intent(this@TasksActivity, EditTaskActivity::class.java)
                    intent.putExtra(TASK_ID, taskEntity.id)
                    startActivity(intent)
                }
            })
        })


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}