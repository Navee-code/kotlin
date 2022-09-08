package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.storage.SharedPrefer
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var  email:EditText
    private lateinit var  conformPassword:EditText
    private lateinit var  password:EditText
    private lateinit var  progressBar:ProgressBar
    private lateinit var binding: ActivitySignUpBinding
    var session = true
    val share= SharedPrefer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getId()
        progressBar.visibility=View.INVISIBLE


    }

    override fun onBackPressed() {

        intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
    private fun getId() {
      progressBar=findViewById(R.id.progress_circular2)
        email=findViewById<TextInputEditText>(R.id.sign_up_user)
        conformPassword=findViewById<TextInputEditText>(R.id.sign_conform_password)
        password=findViewById<TextInputEditText>(R.id.sign_up_password)
    }

    fun signIn(view: View) {
        var emailInput: String?
        var passwordInput: String?
        var conformPasswordInput: String?
        emailInput = email.text.toString()
        passwordInput = password.text.toString()
        conformPasswordInput = conformPassword.text.toString()
        progressBar.visibility=View.VISIBLE
        val db = Firebase.database
        val myRef = db.getReference("KOTLIN").child(emailInput)
        val present = db.reference.child("KOTLIN").ref
if(!emailInput.isEmpty()) {
    present.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val value = snapshot.hasChild(emailInput)

            if (!value) {
                if (!emailInput.isEmpty() && !passwordInput.isEmpty() && !conformPasswordInput.isEmpty()) {
                    if (passwordInput.equals(conformPasswordInput)) {
                        session = false

                        myRef.child("user").setValue(emailInput)
                        myRef.child("Password").setValue(passwordInput)
                        myRef.child("ConformPassword").setValue(conformPasswordInput)
                        progressBar.visibility = View.INVISIBLE
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)

                        share.sharedSetBoolean(true,applicationContext)
                    } else if(session) {
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext, "MissMatch", Toast.LENGTH_SHORT).show()
                    }
                } else if(session) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "Fill the empty", Toast.LENGTH_SHORT).show()
                }

            } else if(session){
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "User Name already exist", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        override fun onCancelled(error: DatabaseError) {
            progressBar.visibility = View.INVISIBLE
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
        }
    })
}
else if(session) {
    progressBar.visibility = View.INVISIBLE
    Toast.makeText(applicationContext, "User Name is empty", Toast.LENGTH_SHORT)
        .show()
}
    }
}



//
//    val  gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestEmail()
//        .build()
//    val gsc= GoogleSignIn.getClient(this,gso)




