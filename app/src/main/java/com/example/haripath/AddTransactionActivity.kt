package com.example.haripath

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Date
import java.util.UUID

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var typeToggleGroup: MaterialButtonToggleGroup
    private lateinit var amountInput: TextInputEditText
    private lateinit var titleInput: TextInputEditText
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var categoryInput: TextInputLayout
    private lateinit var categoryAutoComplete: AutoCompleteTextView
    private lateinit var dateInput: TextInputEditText
    private var selectedDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Transaction"

        // Initialize views
        initializeViews()
        setupTypeToggle()
        setupDatePicker()
        setupSaveButton()
    }

    private fun initializeViews() {
        typeToggleGroup = findViewById(R.id.typeToggleGroup)
        amountInput = findViewById(R.id.amountInput)
        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        categoryInput = findViewById(R.id.categoryInput)
        categoryAutoComplete = findViewById(R.id.categoryAutoComplete)
        dateInput = findViewById(R.id.dateInput)

        // Set up category AutoCompleteTextView
        categoryAutoComplete.apply {
            setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, emptyArray<String>()))
            threshold = 1 // Start showing suggestions after 1 character
            setOnItemClickListener { _, _, position, _ ->
                val selected = adapter.getItem(position) as String
                setText(selected)
            }
        }
    }

    private fun setupTypeToggle() {
        // Set initial categories based on default selection
        updateCategories(typeToggleGroup.checkedButtonId)

        typeToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                updateCategories(checkedId)
            }
        }
    }

    private fun updateCategories(checkedId: Int) {
        val categories = when (checkedId) {
            R.id.btnIncome -> resources.getStringArray(R.array.income_categories)
            R.id.btnExpense -> resources.getStringArray(R.array.expense_categories)
            else -> emptyArray()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        categoryAutoComplete.setAdapter(adapter)
        categoryAutoComplete.setText("") // Clear current selection when type changes
    }

    private fun setupDatePicker() {
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    selectedDate = calendar.time
                    dateInput.setText(android.text.format.DateFormat.format("MMM dd, yyyy", calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSaveButton() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSave).setOnClickListener {
            if (validateInputs()) {
                saveTransaction()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate amount
        if (amountInput.text.isNullOrEmpty()) {
            amountInput.error = "Amount is required"
            isValid = false
        }

        // Validate title
        if (titleInput.text.isNullOrEmpty()) {
            titleInput.error = "Title is required"
            isValid = false
        }

        // Validate category
        if (categoryAutoComplete.text.isNullOrEmpty()) {
            categoryInput.error = "Category is required"
            isValid = false
        }

        return isValid
    }

    private fun saveTransaction() {
        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            amount = amountInput.text.toString().toDouble(),
            type = if (typeToggleGroup.checkedButtonId == R.id.btnIncome) TransactionType.INCOME else TransactionType.EXPENSE,
            category = categoryAutoComplete.text.toString(),
            title = titleInput.text.toString(),
            description = descriptionInput.text.toString(),
            date = selectedDate
        )

        // TODO: Save transaction to database
        Toast.makeText(this, "Transaction saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 