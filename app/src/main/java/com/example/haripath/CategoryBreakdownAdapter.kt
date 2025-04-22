package com.example.haripath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class CategoryBreakdownAdapter : RecyclerView.Adapter<CategoryBreakdownAdapter.ViewHolder>() {
    private var categorySpendings: List<CategorySpending> = emptyList()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "LK"))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryText: TextView = view.findViewById(android.R.id.text1)
        val amountText: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spending = categorySpendings[position]
        holder.categoryText.text = spending.category
        holder.amountText.text = currencyFormat.format(spending.amount)
    }

    override fun getItemCount() = categorySpendings.size

    fun updateData(newData: List<CategorySpending>) {
        categorySpendings = newData.sortedByDescending { it.amount }
        notifyDataSetChanged()
    }
} 