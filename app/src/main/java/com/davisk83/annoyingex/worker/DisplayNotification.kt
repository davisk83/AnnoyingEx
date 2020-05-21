package com.davisk83.annoyingex.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.davisk83.annoyingex.R
import com.davisk83.annoyingex.activity.MainActivity
import com.davisk83.annoyingex.application.AnnoyingExApp
import kotlin.random.Random

class DisplayNotification(private val context: Context, workParams: WorkerParameters): Worker(context, workParams) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private lateinit var messages: List<String>

    companion object {
        const val MESSAGE_CHANNEL = "MESSAGE_CHANNEL_ID"
    }

    init {
        createMessageChannel()
        val apiManager = (context as AnnoyingExApp).apiManager
        apiManager.getMessages { allMessages ->
            messages = allMessages.messages
        }
    }

    override fun doWork(): Result {
        displayNotification()
        return Result.success()
    }

    private fun displayNotification() {
        val randomMessageIndex = Random.nextInt(0, messages.size)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("message", messages[randomMessageIndex])
        }
        val pendingNotificationIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, MESSAGE_CHANNEL)
            .setSmallIcon(R.drawable.ic_chat_blue_24dp)
            .setContentTitle("Regina George")
            .setContentText(messages[randomMessageIndex])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingNotificationIntent)
            .setAutoCancel(true)
            .build()

        notificationManagerCompat.notify(Random.nextInt(), notification)
    }

    private fun createMessageChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Message Notifications"
        val descriptionText = "Texts from Annoying Ex"
        val importance = NotificationManager.IMPORTANCE_DEFAULT // build-in android Notification Manager
        val channel = NotificationChannel(MESSAGE_CHANNEL, name, importance).apply {
            description = descriptionText
        }
        notificationManagerCompat.createNotificationChannel(channel)
    }
}