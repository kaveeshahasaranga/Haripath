package com.example.haripath

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.text.NumberFormat
import android.widget.TextView

class TransactionHistoryActivity : AppCompatActivity() {
    private lateinit var transactionsRecyclerView: RecyclerView
    private lateinit var filterChipGroup: ChipGroup
    private lateinit var fabFilter: FloatingActionButton
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var totalIncomeText: TextView
    private lateinit var totalExpensesText: TextView
    private lateinit var netBalanceText: TextView

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private var selectedStartDate: Date? = null
    private var selectedEndDate: Date? = null
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        setupToolbar()
        initializeViews()
        setupRecyclerView()
        setupFilterChips()
        setupFabFilter()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView)
        filterChipGroup = findViewById(R.id.filterChipGroup)
        fabFilter = findViewById(R.id.fabFilter)
    }

    private fun setupRecyclerView() {
        // Sample transactions - replace with actual data from database
        val transactions = listOf(
            Transaction(
                id = "1",
                amount = 5000.0,
                type = TransactionType.INCOME,
                category = "Salary",
                title = "Monthly Salary",
                description = "Monthly salary payment",
                date = Date()
            ),
            Transaction(
                id = "2",
                amount = 1500.0,
                type = TransactionType.EXPENSE,
                category = "Food",
                title = "Lunch at Restaurant",
                description = "Lunch with colleagues",
                date = Date()
            )
        )

        transactionAdapter = TransactionAdapter(transactions) { transaction ->
            showTransactionOptions(transaction)
        }

        transactionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TransactionHistoryActivity)
            adapter = transactionAdapter
        }
    }

    private fun setupFilterChips() {
        filterChipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.allChip -> filterTransactions(null)
                R.id.incomeChip -> filterTransactions(TransactionType.INCOME)
                R.id.expenseChip -> filterTransactions(TransactionType.EXPENSE)
            }
        }
    }

    private fun setupFabFilter() {
        fabFilter.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        val filterOptions = arrayOf("Date Range", "Category")
        MaterialAlertDialogBuilder(this)
            .setTitle("Filter Options")
            .setItems(filterOptions) { _, which ->
                when (which) {
                    0 -> showDateRangePicker()
                    1 -> showCategoryPicker()
                }
            }
            .show()
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
                filterTransactions()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        endDatePicker.show()
    }

    private fun showCategoryPicker() {
        val categories = arrayOf(
            "Salary",
            "Food",
            "Transport",
            "Shopping",
            "Entertainment",
            "Bills",
            "Other"
        )

        MaterialAlertDialogBuilder(this)
            .setTitle("Select Category")
            .setItems(categories) { _, which ->
                selectedCategory = categories[which]
                filterTransactions()
            }
            .show()
    }

    private fun filterTransactions(type: TransactionType? = null) {
        // TODO: Implement actual filtering logic with database
        // For now, just show a toast
        val filterText = buildString {
            if (type != null) append("Type: ${type.name}, ")
            if (selectedStartDate != null) append("Start: ${dateFormat.format(selectedStartDate)}, ")
            if (selectedEndDate != null) append("End: ${dateFormat.format(selectedEndDate)}, ")
            if (selectedCategory != null) append("Category: $selectedCategory")
        }
        Toast.makeText(this, "Filtering by: $filterText", Toast.LENGTH_SHORT).show()
    }

    private fun showTransactionOptions(transaction: Transaction) {
        val options = arrayOf("Edit", "Delete")
        MaterialAlertDialogBuilder(this)
            .setTitle("Transaction Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editTransaction(transaction)
                    1 -> deleteTransaction(transaction)
                }
            }
            .show()
    }

    private fun editTransaction(transaction: Transaction) {
        // TODO: Implement edit functionality
        Toast.makeText(this, "Edit transaction: ${transaction.description}", Toast.LENGTH_SHORT).show()
    }

    private fun deleteTransaction(transaction: Transaction) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Delete") { _, _ ->
                // TODO: Implement delete functionality
                Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 