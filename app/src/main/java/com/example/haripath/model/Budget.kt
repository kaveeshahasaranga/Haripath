package com.example.haripath.model

import org.json.JSONObject
import java.util.Date

data class Budget(
    val id: String = Date().time.toString(),
    val name: String,
    val amount: Double,
    val category: String,
    val startDate: Long,
    val endDate: Long,
    val spent: Double = 0.0
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("amount", amount)
            put("category", category)
            put("startDate", startDate)
            put("endDate", endDate)
            put("spent", spent)
        }
    }

    companion object {
        fun fromJson(json: JSONObject): Budget {
            return Budget(
                id = json.getString("id"),
                name = json.getString("name"),
                amount = json.getDouble("amount"),
                category = json.getString("category"),
                startDate = json.getLong("startDate"),
                endDate = json.getLong("endDate"),
                spent = json.optDouble("spent", 0.0)
            )
        }
    }
} 