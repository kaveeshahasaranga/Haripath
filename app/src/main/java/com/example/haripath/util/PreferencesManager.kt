package com.example.haripath.util

import android.content.Context
import android.content.SharedPreferences
import com.example.haripath.model.Budget
import org.json.JSONObject
import java.util.*

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "HaripathPrefs"
        private const val KEY_CURRENCY = "currency"
        private const val KEY_BUDGETS = "budgets"
        private const val KEY_DAILY_REMINDER = "daily_reminder"
        private const val KEY_BUDGET_ALERTS = "budget_alerts"
        private const val KEY_BUDGET_ALERT_THRESHOLD = "budget_alert_threshold"
    }

    // Currency settings
    var currency: String
        get() = prefs.getString(KEY_CURRENCY, "USD") ?: "USD"
        set(value) = prefs.edit().putString(KEY_CURRENCY, value).apply()

    // Budget settings
    fun saveBudget(budget: Budget) {
        val budgetsJson = prefs.getString(KEY_BUDGETS, "{}") ?: "{}"
        val budgets = JSONObject(budgetsJson)
        budgets.put(budget.id, budget.toJson())
        prefs.edit().putString(KEY_BUDGETS, budgets.toString()).apply()
    }

    fun getBudgets(): List<Budget> {
        val budgetsJson = prefs.getString(KEY_BUDGETS, "{}") ?: "{}"
        val budgets = JSONObject(budgetsJson)
        return budgets.keys().asSequence().map { key ->
            Budget.fromJson(budgets.getJSONObject(key))
        }.toList()
    }

    fun deleteBudget(budgetId: String) {
        val budgetsJson = prefs.getString(KEY_BUDGETS, "{}") ?: "{}"
        val budgets = JSONObject(budgetsJson)
        budgets.remove(budgetId)
        prefs.edit().putString(KEY_BUDGETS, budgets.toString()).apply()
    }

    // Notification settings
    var dailyReminderEnabled: Boolean
        get() = prefs.getBoolean(KEY_DAILY_REMINDER, false)
        set(value) = prefs.edit().putBoolean(KEY_DAILY_REMINDER, value).apply()

    var budgetAlertsEnabled: Boolean
        get() = prefs.getBoolean(KEY_BUDGET_ALERTS, true)
        set(value) = prefs.edit().putBoolean(KEY_BUDGET_ALERTS, value).apply()

    var budgetAlertThreshold: Int
        get() = prefs.getInt(KEY_BUDGET_ALERT_THRESHOLD, 80) // Default to 80%
        set(value) = prefs.edit().putInt(KEY_BUDGET_ALERT_THRESHOLD, value).apply()
} 