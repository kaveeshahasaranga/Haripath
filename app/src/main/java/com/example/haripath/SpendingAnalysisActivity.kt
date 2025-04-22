package com.example.haripath

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SpendingAnalysisActivity : AppCompatActivity() {
    private lateinit var spendingChart: PieChart
    private lateinit var totalSpendingText: TextView
    private lateinit var timeFilterChipGroup: ChipGroup
    private lateinit var categoryBreakdownRecyclerView: RecyclerView
    private lateinit var categoryBreakdownAdapter: CategoryBreakdownAdapter

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "LK"))
    private var selectedStartDate: Date? = null
    private var selectedEndDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_analysis)

        setupToolbar()
        initializeViews()
        setupChart()
        setupTimeFilters()
        setupCategoryBreakdown()
        updateAnalysis()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        spendingChart = findViewById(R.id.spendingChart)
        totalSpendingText = findViewById(R.id.totalSpendingText)
        timeFilterChipGroup = findViewById(R.id.timeFilterChipGroup)
        categoryBreakdownRecyclerView = findViewById(R.id.categoryBreakdownRecyclerView)
    }

    private fun setupChart() {
        spendingChart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setEntryLabelColor(resources.getColor(android.R.color.black))
            setHoleColor(resources.getColor(android.R.color.transparent))
            setTransparentCircleAlpha(0)
            setDrawEntryLabels(true)
            setEntryLabelTextSize(12f)
        }
    }

    private fun setupTimeFilters() {
        timeFilterChipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.thisMonthChip -> {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    selectedStartDate = calendar.time
                    selectedEndDate = Date()
                    updateAnalysis()
                }
                R.id.lastMonthChip -> {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.MONTH, -1)
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    selectedStartDate = calendar.time
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                    selectedEndDate = calendar.time
                    updateAnalysis()
                }
                R.id.customRangeChip -> {
                    showDateRangePicker()
                }
            }
        }
    }

    private fun showDateRangePicker() {
        val startDatePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedStartDate = calendar.time
                showEndDatePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        startDatePicker.show()
    }

    private fun showEndDatePicker() {
        val endDatePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedEndDate = calendar.time
                updateAnalysis()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        endDatePicker.show()
    }

    private fun setupCategoryBreakdown() {
        categoryBreakdownAdapter = CategoryBreakdownAdapter()
        categoryBreakdownRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SpendingAnalysisActivity)
            adapter = categoryBreakdownAdapter
        }
    }

    private fun updateAnalysis() {
        // TODO: Replace with actual data from database
        val sampleData = listOf(
            CategorySpending("Food", 5000.0),
            CategorySpending("Transport", 3000.0),
            CategorySpending("Shopping", 2000.0),
            CategorySpending("Entertainment", 1500.0),
            CategorySpending("Bills", 4000.0)
        )

        // Update total spending
        val totalSpending = sampleData.sumOf { it.amount }
        totalSpendingText.text = currencyFormat.format(totalSpending)

        // Update pie chart
        val entries = sampleData.map { PieEntry(it.amount.toFloat(), it.category) }
        val dataSet = PieDataSet(entries, "Spending by Category")
        dataSet.colors = listOf(
            resources.getColor(android.R.color.holo_blue_light),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_orange_light),
            resources.getColor(android.R.color.holo_red_light),
            resources.getColor(android.R.color.holo_purple)
        )
        spendingChart.data = PieData(dataSet)
        spendingChart.invalidate()

        // Update category breakdown
        categoryBreakdownAdapter.updateData(sampleData)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

data class CategorySpending(
    val category: String,
    val amount: Double
) 