package bettinger.david.taskchainapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.net.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

open class TasksAdapter(
    private val context: Context,
    private var list: List<TaskEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_task,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_task_name.text = model.name
            holder.itemView.tv_task_description.text = model.description

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
        fun onClick(position: Int, taskEntity: TaskEntity)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}