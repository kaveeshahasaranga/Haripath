package com.example.haripath.model

import org.json.JSONObject
import java.util.Date

data class BackupData(
    val version: String = "1.0",
    val timestamp: Long = Date().time,
    val transactions: List<Transaction>,
    val budgets: List<Budget>,
    val categories: List<Category>
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("version", version)
            put("timestamp", timestamp)
            put("transactions", JSONObject().apply {
                transactions.forEach { transaction ->
                    put(transaction.id, transaction.toJson())
                }
            })
            put("budgets", JSONObject().apply {
                budgets.forEach { budget ->
                    put(budget.id, budget.toJson())
                }
            })
            put("categories", JSONObject().apply {
                categories.forEach { category ->
                    put(category.id, category.toJson())
                }
            })
        }
    }

    companion object {
        fun fromJson(json: JSONObject): BackupData {
            return BackupData(
                version = json.optString("version", "1.0"),
                timestamp = json.optLong("timestamp", Date().time),
                transactions = json.optJSONObject("transactions")?.let { transactionsJson ->
                    transactionsJson.keys().asSequence().map { key ->
                        Transaction.fromJson(transactionsJson.getJSONObject(key))
                    }.toList()
                } ?: emptyList(),
                budgets = json.optJSONObject("budgets")?.let { budgetsJson ->
                    budgetsJson.keys().asSequence().map { key ->
                        Budget.fromJson(budgetsJson.getJSONObject(key))
                    }.toList()
                } ?: emptyList(),
                categories = json.optJSONObject("categories")?.let { categoriesJson ->
                    categoriesJson.keys().asSequence().map { key ->
                        Category.fromJson(categoriesJson.getJSONObject(key))
                    }.toList()
                } ?: emptyList()
            )
        }
    }
} 