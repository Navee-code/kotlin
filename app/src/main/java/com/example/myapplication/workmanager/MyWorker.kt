package com.example.myapplication.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.R
import com.example.myapplication.activity.MainActivity

class MyWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    private val id = "TaskManager NOTIFICATION"
    private val idNotification = 1
    override fun doWork(): Result {
        Log.e("TAG","SUCESS")
        createNofication()
        return Result.success()
    }

    private fun createNofication() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val builder = NotificationCompat.Builder(applicationContext, id)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("The Work Scheduler ")
            .setContentText("Success")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(idNotification, builder.build())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name: CharSequence = "OTP NOTIFICATION"
                val Channeldescription = "Include all the simple notification"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val notificationChannel = NotificationChannel(id, name, importance).apply {
                    description = Channeldescription
                }


                val notificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannel)
            }
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(idNotification,builder.build())
            }
        }

    }
}