package com.qadomy.to_do_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.qadomy.to_do_app.R
import com.qadomy.to_do_app.data.model.Priority
import com.qadomy.to_do_app.data.model.ToDo
import com.qadomy.to_do_app.screens.list_screen.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var dataList = emptyList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.row_text_title.text = dataList[position].title
        holder.itemView.row_text_description.text = dataList[position].description

        holder.itemView.row_background.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }


        when (dataList[position].priority) {
            //todo
            Priority.HIGH -> holder.itemView.row_priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark)
            )
            Priority.MEDIUM -> holder.itemView.row_priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_purple)
            )
            Priority.LOW -> holder.itemView.row_priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark)
            )
        }
    }


    fun setData(toDoData: List<ToDo>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}