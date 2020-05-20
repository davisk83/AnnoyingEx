package com.davisk83.annoyingex

import android.app.Application

class AnnoyingExApp: Application() {

    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
    }
}