package com.example.haripath

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class TransactionsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView = view.findViewById(R.id.transactionsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data - replace with actual data from your database
        val transactions = listOf(
            Transaction(
                id = "1",
                amount = 5000.0,
                type = TransactionType.INCOME,
                category = "Salary",
                title = "Monthly Salary",
                description = "Monthly salary from company",
                date = Date()
            ),
            Transaction(
                id = "2",
                amount = 1500.0,
                type = TransactionType.EXPENSE,
                category = "Food",
                title = "Lunch at Restaurant",
                description = "Team lunch at local restaurant",
                date = Date()
            )
        )

        recyclerView.adapter = TransactionAdapter(transactions) { transaction ->
            // TODO: Show transaction details
        }
    }
} 