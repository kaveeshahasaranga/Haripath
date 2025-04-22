package com.example.haripath

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale

class BudgetSetupActivity : AppCompatActivity() {
    private lateinit var currentBudgetText: TextView
    private lateinit var budgetAmountEditText: TextInputEditText
    private lateinit var currencyAutoComplete: AutoCompleteTextView
    private lateinit var saveButton: MaterialButton

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "LK"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_setup)

        setupToolbar()
        initializeViews()
        setupCurrencyDropdown()
        setupSaveButton()
        loadCurrentBudget()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        currentBudgetText = findViewById(R.id.currentBudgetText)
        budgetAmountEditText = findViewById(R.id.budgetAmountEditText)
        currencyAutoComplete = findViewById(R.id.currencyAutoComplete)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun setupCurrencyDropdown() {
        val currencies = arrayOf(
            "LKR - Sri Lankan Rupee",
            "USD - US Dollar",
            "EUR - Euro",
            "GBP - British Pound",
            "INR - Indian Rupee"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, currencies)
        currencyAutoComplete.setAdapter(adapter)

        // Set default currency
        currencyAutoComplete.setText(currencies[0], false)
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            if (validateInput()) {
                saveBudget()
            }
        }
    }

    private fun validateInput(): Boolean {
        val amount = budgetAmountEditText.text.toString().trim()
        if (amount.isEmpty()) {
            budgetAmountEditText.error = "Budget amount is required"
            return false
        }

        try {
            val budgetAmount = amount.toDouble()
            if (budgetAmount <= 0) {
                budgetAmountEditText.error = "Budget amount must be greater than 0"
                return false
            }
        } catch (e: NumberFormatException) {
            budgetAmountEditText.error = "Invalid budget amount"
            return false
        }

        return true
    }

    private fun saveBudget() {
        val amount = budgetAmountEditText.text.toString().toDouble()
        val currency = currencyAutoComplete.text.toString()

        // TODO: Save budget to database
        // For now, just show a toast
        Toast.makeText(this, "Budget saved: $currency $amount", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun loadCurrentBudget() {
        // TODO: Load current budget from database
        // For now, just show a sample value
        val currentBudget = 50000.0
        currentBudgetText.text = currencyFormat.format(currentBudget)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 