package com.davisk83.annoyingex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davisk83.annoyingex.model.Messages
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var apiManager: ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiManager = (application as AnnoyingExApp).apiManager

        fetchTextMessages()
    }

    private fun fetchTextMessages() {
        apiManager.getMessages { allMessages ->
            val textMessages = allMessages.messages
            Log.i("test", "$textMessages")
        }
    }
}
