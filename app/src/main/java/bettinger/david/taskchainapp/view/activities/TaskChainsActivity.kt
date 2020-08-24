package bettinger.david.taskchainapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.utils.Constants
import bettinger.david.taskchainapp.view.adapters.TaskChainsAdapter
import bettinger.david.taskchainapp.viewmodel.TaskChainsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_task_chains.*

@AndroidEntryPoint
class TaskChainsActivity : BaseActivity() {

    companion object {
        const val TASK_CHAIN_ID = "Task-Chain id"

    }

    private val viewModel: TaskChainsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_chains)

        setUpUI()
        viewModel.loadTaskChains()
        getTaskChains()
    }

    private fun setUpUI() {
        setSupportActionBar(toolbar_task_chains)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getText(R.string.task_chains)

        toolbar_task_chains.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel.messageLiveData.observe(this, {
            showMessage(resources.getString(it), this)
        })
    }


    private fun getTaskChains() {

        viewModel.isLoaded.observe(this, {
            it?.let { visibility ->
                if (visibility) hideProgressDialog() else showCustomProgressDialog()
            }
        })


        viewModel.taskChainsLiveData.observe(this, { taskChains ->
            if (taskChains == null) {
                //TODO show message
            } else {
                setupTasksChainsRecyclerView(taskChains)
            }
        })

    }

    private fun setupTasksChainsRecyclerView(taskList: List<TaskChainEntity>) {

        rv_task_chain_list.layoutManager = LinearLayoutManager(this)
        rv_task_chain_list.setHasFixedSize(true)

        val taskChainsAdapter = TaskChainsAdapter(this, taskList)
        rv_task_chain_list.adapter = taskChainsAdapter

        taskChainsAdapter.setOnClickListener(object :
            TaskChainsAdapter.OnClickListener {
            override fun onClick(position: Int, taskChainEntity: TaskChainEntity) {
                val intent = Intent(this@TaskChainsActivity, TasksActivity::class.java)
                intent.putExtra(TASK_CHAIN_ID, taskChainEntity.id)
                startActivity(intent)
            }
        })

    }

}