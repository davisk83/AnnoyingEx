package com.davisk83.annoyingex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davisk83.annoyingex.application.AnnoyingExApp
import com.davisk83.annoyingex.manager.ApiManager
import com.davisk83.annoyingex.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var apiManager: ApiManager
    private lateinit var messages: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiManager = (application as AnnoyingExApp).apiManager

        fetchTextMessages()
        initStartButton()
    }

    private fun fetchTextMessages() {
        apiManager.getMessages { allMessages ->
            messages = allMessages.messages
        }
    }

    private fun initStartButton() {
        btnStartNotification.setOnClickListener {
            (application as AnnoyingExApp).notificationManager.startNotification()
        }
    }
}
