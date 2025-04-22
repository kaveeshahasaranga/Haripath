package com.example.haripath.model

import org.json.JSONObject
import java.util.Date

data class Category(
    val id: String = Date().time.toString(),
    val name: String,
    val type: CategoryType,
    val icon: String? = null,
    val color: String? = null
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("type", type.name)
            put("icon", icon)
            put("color", color)
        }
    }

    companion object {
        fun fromJson(json: JSONObject): Category {
            return Category(
                id = json.getString("id"),
                name = json.getString("name"),
                type = CategoryType.valueOf(json.getString("type")),
                icon = json.optString("icon"),
                color = json.optString("color")
            )
        }
    }
}

enum class CategoryType {
    INCOME,
    EXPENSE
} 