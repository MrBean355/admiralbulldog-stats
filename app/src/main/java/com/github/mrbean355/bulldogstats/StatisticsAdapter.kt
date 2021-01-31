package com.github.mrbean355.bulldogstats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter(
    private val horizontal: Boolean,
    private val onItemClicked: (String) -> Unit = {}
) : ListAdapter<String, StatisticsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (horizontal) R.layout.item_breakdown else R.layout.item_property
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = getItem(position).formatProperty()
        holder.itemView.setOnClickListener {
            onItemClicked(getItem(holder.adapterPosition))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }
    }
}