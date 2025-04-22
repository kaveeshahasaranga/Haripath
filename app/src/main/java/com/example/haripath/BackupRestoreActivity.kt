package com.example.haripath

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haripath.model.BackupData
import com.example.haripath.model.Budget
import com.example.haripath.model.Category
import com.example.haripath.model.Transaction
import com.example.haripath.util.BackupManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import java.io.IOException

class BackupRestoreActivity : AppCompatActivity() {
    private lateinit var btnExportJson: MaterialButton
    private lateinit var btnExportText: MaterialButton
    private lateinit var btnImport: MaterialButton
    private lateinit var statusCard: MaterialCardView
    private lateinit var statusTitle: MaterialTextView
    private lateinit var statusMessage: MaterialTextView
    private lateinit var backupManager: BackupManager

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup_restore)

        backupManager = BackupManager(this)
        setupToolbar()
        initializeViews()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        btnExportJson = findViewById(R.id.btnExportJson)
        btnExportText = findViewById(R.id.btnExportText)
        btnImport = findViewById(R.id.btnImport)
        statusCard = findViewById(R.id.statusCard)
        statusTitle = findViewById(R.id.statusTitle)
        statusMessage = findViewById(R.id.statusMessage)
    }

    private fun setupClickListeners() {
        btnExportJson.setOnClickListener { exportData(ExportFormat.JSON) }
        btnExportText.setOnClickListener { exportData(ExportFormat.TEXT) }
        btnImport.setOnClickListener { importData() }
    }

    private fun exportData(format: ExportFormat) {
        try {
            // Get data to export (replace with your actual data)
            val backupData = getDataToExport()
            
            // Export data using BackupManager
            val backupFile = when (format) {
                ExportFormat.JSON -> backupManager.exportToJson(backupData)
                ExportFormat.TEXT -> backupManager.exportToText(backupData)
            }

            showStatus(getString(R.string.success), getString(R.string.backup_saved, backupFile.absolutePath))
        } catch (e: Exception) {
            showStatus(getString(R.string.error), getString(R.string.backup_failed, e.message))
        }
    }

    private fun importData() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                try {
                    val backupData = backupManager.importFromUri(uri)
                    restoreData(backupData)
                } catch (e: IOException) {
                    showStatus(getString(R.string.error), getString(R.string.import_failed, e.message ?: "Unknown error"))
                }
            }
        }
    }

    private fun restoreData(backupData: BackupData) {
        try {
            // TODO: Implement data restoration logic
            // This is where you would save the imported data to your database
            showStatus(getString(R.string.success), getString(R.string.data_restored))
        } catch (e: Exception) {
            showStatus(getString(R.string.error), getString(R.string.data_restore_failed, e.message))
        }
    }

    private fun getDataToExport(): BackupData {
        // TODO: Replace with your actual data export logic
        return BackupData(
            transactions = emptyList<Transaction>(), // Add your transactions
            budgets = emptyList<Budget>(), // Add your budgets
            categories = emptyList<Category>() // Add your categories
        )
    }

    private fun showStatus(title: String, message: String) {
        statusTitle.text = title
        statusMessage.text = message
        statusCard.visibility = MaterialCardView.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private enum class ExportFormat {
        JSON,
        TEXT
    }
} 