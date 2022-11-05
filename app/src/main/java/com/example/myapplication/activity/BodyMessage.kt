package com.example.myapplication.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class BodyMessage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_message)
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing,);
        val message=findViewById<TextView>(R.id.subject)
        val body=intent.getStringExtra("Body")
        message.text=body
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }
}