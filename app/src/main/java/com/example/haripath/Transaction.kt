package com.example.haripath

import java.util.Date

data class Transaction(
    val id: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val title: String,
    val description: String,
    val date: Date
)

enum class TransactionType {
    INCOME,
    EXPENSE
} 