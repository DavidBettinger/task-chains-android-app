package bettinger.david.taskchainapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.utils.Constants
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(private val context: Context,
    private var list: MutableList<CommentEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    fun addComment(comment: CommentEntity){
        list.add(comment)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_comment_creator.text = model.createdBy.userName
            holder.itemView.tv_comment_date.text = Constants.formatDate(model.createdAt)

            holder.itemView.tv_comment.text = model.text
        }
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}