package com.example.haripath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DestinationAdapter(
    private val destinations: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    class DestinationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val destinationName: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinations[position]
        holder.destinationName.text = destination
        holder.itemView.setOnClickListener { onItemClick(destination) }
    }

    override fun getItemCount() = destinations.size
} 