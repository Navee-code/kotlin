package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.storage.SharedPrefer
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashScreenActivity : AppCompatActivity() {
    val share= SharedPrefer()
    val handler=Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splash=findViewById<ImageView>(R.id.splash_image)
        val textSplash=findViewById<TextView>(R.id.textSplash)
         textSplash.visibility=View.INVISIBLE
        splash.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_fade_in))
        handler.postDelayed({
            splash.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.move))
        },900)
        handler.postDelayed({

            textSplash.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_fade_in))
            textSplash.visibility=View.VISIBLE
        },1200)
    }

    override fun onStart() {

        handler.postDelayed({
        val intent = Intent(applicationContext, MainActivity::class.java)
        val google= GoogleSignIn.getLastSignedInAccount(this)
        if(google!=null){
            Toast.makeText(this,"Google log in", Toast.LENGTH_SHORT).show()
           // finish()
            startActivity(intent)

        }else if(share.sharedGetBoolean(this)){
            Toast.makeText(this,"Real time log in", Toast.LENGTH_SHORT).show()
           // finish()


            startActivity(intent)
        }else{
            val intent = Intent(applicationContext, LoginActivity::class.java)
           // finish()
            startActivity(intent)
        }
        },2500)
        super.onStart()
    }
}