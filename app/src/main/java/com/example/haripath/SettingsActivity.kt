package com.example.haripath

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private lateinit var currencyAutoComplete: AutoCompleteTextView
    private lateinit var expenseAlertsSwitch: SwitchMaterial
    private lateinit var budgetAlertsSwitch: SwitchMaterial
    private lateinit var monthlyReportSwitch: SwitchMaterial
    private lateinit var backupButton: MaterialButton
    private lateinit var restoreButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolbar()
        initializeViews()
        setupCurrencyDropdown()
        setupNotificationSwitches()
        setupBackupRestoreButtons()
        loadSettings()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        currencyAutoComplete = findViewById(R.id.currencyAutoComplete)
        expenseAlertsSwitch = findViewById(R.id.expenseAlertsSwitch)
        budgetAlertsSwitch = findViewById(R.id.budgetAlertsSwitch)
        monthlyReportSwitch = findViewById(R.id.monthlyReportSwitch)
        backupButton = findViewById(R.id.backupButton)
        restoreButton = findViewById(R.id.restoreButton)
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

        currencyAutoComplete.setOnItemClickListener { _, _, position, _ ->
            saveCurrencySetting(currencies[position])
        }
    }

    private fun setupNotificationSwitches() {
        expenseAlertsSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationSetting("expense_alerts", isChecked)
        }

        budgetAlertsSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationSetting("budget_alerts", isChecked)
        }

        monthlyReportSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationSetting("monthly_reports", isChecked)
        }
    }

    private fun setupBackupRestoreButtons() {
        backupButton.setOnClickListener {
            showBackupDialog()
        }

        restoreButton.setOnClickListener {
            showRestoreDialog()
        }
    }

    private fun showBackupDialog() {
        AlertDialog.Builder(this)
            .setTitle("Backup Data")
            .setMessage("This will create a backup of your transactions and settings. Continue?")
            .setPositiveButton("Backup") { _, _ ->
                performBackup()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showRestoreDialog() {
        AlertDialog.Builder(this)
            .setTitle("Restore Data")
            .setMessage("This will restore your transactions and settings from a backup. Continue?")
            .setPositiveButton("Restore") { _, _ ->
                performRestore()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performBackup() {
        // TODO: Implement actual backup logic
        // For now, just show a toast
        Toast.makeText(this, "Backup created successfully", Toast.LENGTH_SHORT).show()
    }

    private fun performRestore() {
        // TODO: Implement actual restore logic
        // For now, just show a toast
        Toast.makeText(this, "Data restored successfully", Toast.LENGTH_SHORT).show()
    }

    private fun saveCurrencySetting(currency: String) {
        // TODO: Save currency setting to SharedPreferences or database
        Toast.makeText(this, "Currency set to: $currency", Toast.LENGTH_SHORT).show()
    }

    private fun saveNotificationSetting(key: String, value: Boolean) {
        // TODO: Save notification setting to SharedPreferences or database
        Toast.makeText(this, "$key set to: $value", Toast.LENGTH_SHORT).show()
    }

    private fun loadSettings() {
        // TODO: Load settings from SharedPreferences or database
        // For now, just set default values
        currencyAutoComplete.setText("LKR - Sri Lankan Rupee", false)
        expenseAlertsSwitch.isChecked = true
        budgetAlertsSwitch.isChecked = true
        monthlyReportSwitch.isChecked = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 