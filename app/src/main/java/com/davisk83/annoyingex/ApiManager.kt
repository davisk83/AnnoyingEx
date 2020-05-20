package com.davisk83.annoyingex

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davisk83.annoyingex.model.Messages
import com.google.gson.Gson

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getMessages(onMessagesReady: (Messages) -> Unit) {
        val messagesURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

        val request = StringRequest(
            Request.Method.GET, messagesURL,
            { response ->
                val gson = Gson()
                val allMessages = gson.fromJson(response, Messages::class.java)

                onMessagesReady(allMessages)
            },
            {
                // Display Non-trivial error message
            }
        )

        queue.add(request)
    }
}