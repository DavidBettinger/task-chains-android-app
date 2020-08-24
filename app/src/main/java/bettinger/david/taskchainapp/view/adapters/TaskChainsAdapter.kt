package bettinger.david.taskchainapp.view.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import kotlinx.android.synthetic.main.item_task_chain.view.*

open class TaskChainsAdapter(
    private val context: Context,
    private var list: List<TaskChainEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_task_chain,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_task_chain_name.text = model.name
            holder.itemView.tv_task_chain_description.text = model.description

            holder.itemView.tv_task_chain_progress.text = "${model.getNumberOfCompletedTasks()} / ${model.tasks.size}"

            if(model.completed){
                holder.itemView.tv_task_chain_completed.text = context.getString(R.string.completed_true)
            } else {
                holder.itemView.tv_task_chain_completed.text = context.getString(R.string.completed_false)
            }

            holder.itemView.setOnClickListener {

                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, taskChainEntity: TaskChainEntity)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}