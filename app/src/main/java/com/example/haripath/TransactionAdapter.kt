package com.example.haripath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onTransactionClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "LK"))

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.transactionTitle)
        val amountText: TextView = view.findViewById(R.id.transactionAmount)
        val categoryText: TextView = view.findViewById(R.id.transactionCategory)
        val dateText: TextView = view.findViewById(R.id.transactionDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        
        holder.titleText.text = transaction.title
        holder.amountText.text = currencyFormat.format(transaction.amount)
        holder.categoryText.text = transaction.category
        holder.dateText.text = dateFormat.format(transaction.date)

        // Set text color based on transaction type
        val colorRes = when (transaction.type) {
            TransactionType.INCOME -> R.color.green_500
            TransactionType.EXPENSE -> R.color.red_500
        }
        holder.amountText.setTextColor(holder.itemView.context.getColor(colorRes))

        holder.itemView.setOnClickListener { onTransactionClick(transaction) }
    }

    override fun getItemCount() = transactions.size
} 