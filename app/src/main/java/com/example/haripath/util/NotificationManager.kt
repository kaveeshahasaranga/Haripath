package com.example.haripath.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.haripath.MainActivity
import com.example.haripath.R
import java.util.*

class NotificationManager(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val preferencesManager = PreferencesManager(context)

    companion object {
        private const val CHANNEL_ID = "HaripathChannel"
        private const val BUDGET_ALERT_ID = 1
        private const val DAILY_REMINDER_ID = 2
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Haripath Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for budget alerts and daily reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showBudgetAlert(category: String, spent: Double, budget: Double) {
        if (!preferencesManager.budgetAlertsEnabled) return

        val percentage = (spent / budget * 100).toInt()
        if (percentage >= preferencesManager.budgetAlertThreshold) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Budget Alert")
                .setContentText("You've spent $percentage% of your $category budget")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(BUDGET_ALERT_ID, notification)
        }
    }

    fun scheduleDailyReminder() {
        if (!preferencesManager.dailyReminderEnabled) return

        // TODO: Implement using WorkManager for daily reminders
        // This is a placeholder for the actual implementation
    }

    fun cancelDailyReminder() {
        notificationManager.cancel(DAILY_REMINDER_ID)
    }
} 