package com.davisk83.annoyingex.application

import android.app.Application
import com.davisk83.annoyingex.manager.ApiManager
import com.davisk83.annoyingex.manager.NotificationManager

class AnnoyingExApp(): Application() {

    lateinit var apiManager: ApiManager
        private set
    lateinit var notificationManager: NotificationManager
        private set

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
        notificationManager = NotificationManager(this)
    }
}