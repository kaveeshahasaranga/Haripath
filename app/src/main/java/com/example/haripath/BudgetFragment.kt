package com.example.haripath

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.progressindicator.LinearProgressIndicator
import android.widget.TextView

class BudgetFragment : Fragment() {
    private lateinit var budgetProgressBar: LinearProgressIndicator
    private lateinit var budgetProgressText: TextView
    private lateinit var expensesChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        budgetProgressBar = view.findViewById(R.id.budgetProgressBar)
        budgetProgressText = view.findViewById(R.id.budgetProgressText)
        expensesChart = view.findViewById(R.id.expensesChart)

        updateBudgetProgress()
        setupChart()
    }

    private fun updateBudgetProgress() {
        // Sample data - replace with actual data from your database
        val totalExpenses = 25000.0
        val monthlyBudget = 30000.0
        val budgetProgress = (totalExpenses / monthlyBudget * 100).toInt()

        budgetProgressBar.progress = budgetProgress
        budgetProgressText.text = "$budgetProgress% of monthly budget used"
    }

    private fun setupChart() {
        // Sample data for the pie chart
        val entries = listOf(
            PieEntry(30f, "Food"),
            PieEntry(20f, "Transport"),
            PieEntry(15f, "Shopping"),
            PieEntry(35f, "Others")
        )

        val dataSet = PieDataSet(entries, "Expenses by Category")
        dataSet.colors = listOf(
            resources.getColor(android.R.color.holo_blue_light),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_orange_light),
            resources.getColor(android.R.color.holo_red_light)
        )

        val data = PieData(dataSet)
        expensesChart.data = data
        expensesChart.description.isEnabled = false
        expensesChart.legend.isEnabled = true
        expensesChart.setEntryLabelColor(resources.getColor(android.R.color.black))
        expensesChart.invalidate()
    }
} 