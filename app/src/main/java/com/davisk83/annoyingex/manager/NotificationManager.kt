package com.davisk83.annoyingex.manager

import android.content.Context
import androidx.work.*
import com.davisk83.annoyingex.worker.DisplayNotification
import java.util.concurrent.TimeUnit

class NotificationManager(private val context: Context) {

    private var workManager = WorkManager.getInstance(context)

    fun startNotification() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DisplayNotification>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(workRequest)
    }
}