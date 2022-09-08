package com.example.myapplication.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityForgotPasswordBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


class ForgotPassword : AppCompatActivity() {
    private val id="OTP NOTIFICATION"
    private val idNotification=1
    private lateinit var binding: ActivityForgotPasswordBinding
    val otp = List(1) { Random.nextInt(1000, 9999) }
    private lateinit var otpValidate:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newPassword.isEnabled=false
        binding.textInputLayout4.isEnabled=false
        binding.textInputLayout3.isEnabled=false
        binding.reEnterPassword.isEnabled=false
        binding.enterOtp.isEnabled=false

    }

    fun sendOtp(view: View) {

        var user:String?
        user=binding.forgotUser.text.toString()
        val db = Firebase.database
        val myRef = db.getReference("KOTLIN").child(user)
        val present = db.reference.child("KOTLIN").ref

        present.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.hasChild(user)
                if(!binding.enterOtp.isEnabled) {

                    if (value != null) {
                        if (!user.isEmpty()) {
                            createNotification()
                            sendNotification()
                            binding.enterOtp.isEnabled = true
                            binding.button.text="VERIFY"


                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Fill the user name",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }


                    } else {
                        Toast.makeText(applicationContext, "User Not found", Toast.LENGTH_SHORT)
                            .show()

                    }
                } else if(otpValidate.equals(binding.enterOtp.text.toString())){
                    myRef.child("Password").setValue(otpValidate)
                    myRef.child("ConformPassword").setValue(otpValidate)
                    Toast.makeText(applicationContext, "Password changed successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {


            }
        })


    }

    private fun sendNotification() {
        val builder=NotificationCompat.Builder(this,id)
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
        builder.setContentTitle("The OTP for changing password ")
        otpValidate=otp.toString()
        builder.setContentText(otpValidate)
        builder.priority=NotificationCompat.PRIORITY_DEFAULT

        val notificationMangerCompat=NotificationManagerCompat.from(this)
        notificationMangerCompat.notify(idNotification,builder.build())
    }

    private fun createNotification() {
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
           val name: CharSequence = "OTP NOTIFICATION"
           val description = "Include all the simple notification"
           val importance = NotificationManager.IMPORTANCE_DEFAULT
           val notificationChannel = NotificationChannel(id, name, importance)
           notificationChannel.description = description

           val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
           notificationManager.createNotificationChannel(notificationChannel)

       }
    }
}






