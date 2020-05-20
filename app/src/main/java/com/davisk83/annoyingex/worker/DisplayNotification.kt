package com.davisk83.annoyingex.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DisplayNotification(private val context: Context, workParams: WorkerParameters): Worker(context, workParams) {

    override fun doWork(): Result {
        Log.i("test", "KITA SUDAH SAMPE BELOM?")

        return Result.success()
    }
}