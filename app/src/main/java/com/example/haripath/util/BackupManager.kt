package com.example.haripath.util

import android.content.Context
import android.net.Uri
import com.example.haripath.model.BackupData
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class BackupManager(private val context: Context) {
    private val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    private val backupFolder = "HaripathBackups"

    fun exportToJson(backupData: BackupData): File {
        val backupDir = File(context.getExternalFilesDir(null), backupFolder)
        if (!backupDir.exists()) {
            backupDir.mkdirs()
        }

        val filename = "haripath_backup_${dateFormat.format(Date())}.json"
        val backupFile = File(backupDir, filename)

        backupFile.writeText(backupData.toJson().toString(4))
        return backupFile
    }

    fun exportToText(backupData: BackupData): File {
        val backupDir = File(context.getExternalFilesDir(null), backupFolder)
        if (!backupDir.exists()) {
            backupDir.mkdirs()
        }

        val filename = "haripath_backup_${dateFormat.format(Date())}.txt"
        val backupFile = File(backupDir, filename)

        val textData = buildString {
            appendLine("Haripath Backup")
            appendLine("Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}")
            appendLine("Version: ${backupData.version}")
            appendLine()
            
            appendLine("Transactions (${backupData.transactions.size}):")
            backupData.transactions.forEach { transaction ->
                appendLine("- ${transaction.title}: ${transaction.amount} (${transaction.category})")
            }
            appendLine()
            
            appendLine("Budgets (${backupData.budgets.size}):")
            backupData.budgets.forEach { budget ->
                appendLine("- ${budget.name}: ${budget.amount} (${budget.category})")
            }
            appendLine()
            
            appendLine("Categories (${backupData.categories.size}):")
            backupData.categories.forEach { category ->
                appendLine("- ${category.name}")
            }
        }

        backupFile.writeText(textData)
        return backupFile
    }

    fun importFromUri(uri: Uri): BackupData {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val backupData = inputStream.bufferedReader().use { it.readText() }
            return try {
                // Try to parse as JSON first
                BackupData.fromJson(JSONObject(backupData))
            } catch (e: Exception) {
                throw IOException("Invalid backup file format")
            }
        } ?: throw IOException("Could not read backup file")
    }

    fun getBackupFiles(): List<File> {
        val backupDir = File(context.getExternalFilesDir(null), backupFolder)
        return if (backupDir.exists()) {
            backupDir.listFiles()?.filter { it.isFile }?.sortedByDescending { it.lastModified() } ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun deleteBackup(file: File): Boolean {
        return file.delete()
    }
} 