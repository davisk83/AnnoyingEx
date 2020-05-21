package com.davisk83.annoyingex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davisk83.annoyingex.application.AnnoyingExApp
import com.davisk83.annoyingex.R
import com.davisk83.annoyingex.manager.ApiManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var apiManager: ApiManager
    private lateinit var messages: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchMessages()
        initStartButton()
        initStopButton()

        val dataFromNotification = intent.getStringExtra("message")
        if (dataFromNotification != null) {
            tvMessage.text = dataFromNotification
        }
    }

    private fun fetchMessages() {
        apiManager = (application as AnnoyingExApp).apiManager
        apiManager.getMessages { allMessages ->
            messages = allMessages.messages
        }
    }

    private fun initStartButton() {
        btnStartNotification.setOnClickListener {
            (application as AnnoyingExApp).notificationManager.startNotification()
        }
    }

    private fun initStopButton() {
        btnStopNotification.setOnClickListener {
            (application as AnnoyingExApp).notificationManager.stopNotification()
        }
    }
}
