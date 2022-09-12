package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.work.*
import com.example.myapplication.workmanager.MyWorker
import com.example.myapplication.R
import com.example.myapplication.data.UserData
import com.example.myapplication.data.UserDataBase
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class CreateTodoTask : AppCompatActivity() {
    private lateinit var userDataBase: UserDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo_task)
        var text = findViewById<TextInputLayout>(R.id.todo_edit_text)
        var create = findViewById<Button>(R.id.create_todo)
        val delete = findViewById<Button>(R.id.delete)
        var intent=Intent(this,MainActivity::class.java)
        delete.setOnClickListener {
            GlobalScope.launch {
                userDataBase.userDao().deleteALl()
                withContext (Dispatchers.Main){

                    startActivity(intent)
                }

            }

        }
        userDataBase = UserDataBase.getDatabase(this)
        Work()

        create.setOnClickListener {
            var task: String = text.editText?.text.toString()
            if (!task.isEmpty()) {
                writeData(task)
                startActivity(intent)
            }


        }
    }

    private fun writeData(task: String) {
        val user = UserData(null, task)
        GlobalScope.launch(Dispatchers.IO) {
            userDataBase.userDao().addNotes(user)
        }

    }

    private fun Work() {
        val constraints = androidx.work.Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()
        val request = PeriodicWorkRequest.Builder(
            MyWorker::class.java, 15, TimeUnit.MINUTES
        ).setConstraints(constraints).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP, request)


    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    //    fun worLog(view: View) {
//        val request: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
//            .build()
    //   Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show()
//        WorkManager.getInstance(this)
//            .enqueue(request)
//    }
    fun worLog(view: View) {

    }
}

