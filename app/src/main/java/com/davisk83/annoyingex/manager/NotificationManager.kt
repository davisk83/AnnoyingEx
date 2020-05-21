package com.davisk83.annoyingex.manager

import android.content.Context
import androidx.work.*
import com.davisk83.annoyingex.worker.DisplayNotification
import java.util.concurrent.TimeUnit

class NotificationManager(context: Context) {

    private var workManager = WorkManager.getInstance(context)

    companion object {
        const val PERIODIC_NOTIFICATION_WORK_REQUEST_TAG = "PERIODIC_NOTIFICATION_WORK_REQUEST_TAG"
    }

    fun startNotification() {
        if (!isRunning()) {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()

            val fullyChargedConstraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<DisplayNotification>(20, TimeUnit.MINUTES)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .addTag(PERIODIC_NOTIFICATION_WORK_REQUEST_TAG)
                .build()

            val fullyChargedWorkRequest = PeriodicWorkRequestBuilder<DisplayNotification>(2, TimeUnit.DAYS)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(fullyChargedConstraints)
                .addTag(PERIODIC_NOTIFICATION_WORK_REQUEST_TAG)
                .build()

            workManager.enqueue(workRequest)
            workManager.enqueue(fullyChargedWorkRequest)
        }
    }

    private fun isRunning(): Boolean {
        return when(workManager.getWorkInfosByTag(PERIODIC_NOTIFICATION_WORK_REQUEST_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

    fun stopNotification() {
        workManager.cancelAllWorkByTag(PERIODIC_NOTIFICATION_WORK_REQUEST_TAG)
    }
}