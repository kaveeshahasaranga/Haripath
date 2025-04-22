package com.example.haripath.model

import org.json.JSONObject
import java.util.Date

data class Transaction(
    val id: String = Date().time.toString(),
    val title: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val type: TransactionType,
    val note: String? = null
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("title", title)
            put("amount", amount)
            put("category", category)
            put("date", date)
            put("type", type.name)
            put("note", note)
        }
    }

    companion object {
        fun fromJson(json: JSONObject): Transaction {
            return Transaction(
                id = json.getString("id"),
                title = json.getString("title"),
                amount = json.getDouble("amount"),
                category = json.getString("category"),
                date = json.getLong("date"),
                type = TransactionType.valueOf(json.getString("type")),
                note = json.optString("note")
            )
        }
    }
}

enum class TransactionType {
    INCOME,
    EXPENSE
} 