package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.storage.SharedPrefer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    val db = Firebase.database
    val share= SharedPrefer()
    private lateinit var button:Button
    private lateinit var sign:TextView
    private lateinit var userEmail:TextInputEditText
    private lateinit var logPassword:TextInputEditText
    private lateinit var progressBar:ProgressBar
    private lateinit var googleSign:SignInButton

    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing,);

        getId()
            progressBar.visibility = View.INVISIBLE


            googleSign.setOnClickListener {
               go()
            }
            sign.setOnClickListener {
                finish()
                var intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            button.setOnClickListener {
                val userName: String?
                val password: String?

                userName = userEmail.text.toString()
                password = logPassword.text.toString()
                progressBar.visibility = View.VISIBLE
                val myRef = db.reference.child("KOTLIN").ref
                if (!userName.isEmpty()) {
                    if (!password.isEmpty()) {
                        myRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                val value = snapshot.child(userName).value

                                if (value != null) {
                                    val passwordFromServer =
                                        snapshot.child(userName).child("Password").value

                                    if (passwordFromServer == password) {
                                       // Log.e("Share before login",share.sharedGetBoolean(applicationContext).toString())
                                        share.sharedSetBoolean(true, applicationContext)

                                        finish()
                                        val intent =
                                            Intent(applicationContext, MainActivity::class.java)
                                        startActivity(intent)
                                       // Log.e("Share After login",share.sharedGetBoolean(applicationContext).toString())

                                        progressBar.visibility = View.INVISIBLE
                                    } else {
                                        progressBar.visibility = View.INVISIBLE
                                        Toast.makeText(
                                            applicationContext,
                                            "Wrong Password",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                } else {
                                    progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(
                                        applicationContext,
                                        "User Not found",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                progressBar.visibility = View.INVISIBLE
                                Toast.makeText(applicationContext, error.message.toString(), Toast.LENGTH_SHORT).show()

                            }
                        })
                    } else {
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext, "Fill the password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "Fill the user name", Toast.LENGTH_SHORT)
                        .show()
                }
            }


    }

    private fun getId() {
        button = findViewById(R.id.login)
        sign = findViewById(R.id.register)
        userEmail = findViewById(R.id.login_user)
        logPassword = findViewById(R.id.login_password)
        progressBar = findViewById(R.id.progress_circular)
        googleSign=findViewById(R.id.google_auth)
    }




    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(a)
        super.onBackPressed()
    }

    fun go(){
        val  gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val gsc= GoogleSignIn.getClient(this,gso)
        auth=FirebaseAuth.getInstance()
        intent=gsc.signInIntent
        startActivityForResult(intent,120)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==120){
        val task= GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
            if(task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)
                 fireBaseAuth(account.idToken!!)


                }catch (e:ApiException){
                    Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show()
            }


        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun fireBaseAuth(idToken: String) {
        val credential=GoogleAuthProvider.getCredential(idToken,null)
         auth.signInWithCredential(credential)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {

                     val intent = Intent(applicationContext, MainActivity::class.java)
                     startActivity(intent)

                 } else {
                     Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                 }
             }

    }

    override fun onStart() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val google= GoogleSignIn.getLastSignedInAccount(this)
        if(google!=null){
           // Toast.makeText(this,"Google log in",Toast.LENGTH_SHORT).show()
            finish()
            startActivity(intent)

        }else if(share.sharedGetBoolean(this)){
          //  Toast.makeText(this,"Real time log in",Toast.LENGTH_SHORT).show()
            finish()
            startActivity(intent)
        }
        super.onStart()
    }

    fun forgotPassword(view: View) {
        val intent=Intent(applicationContext, ForgotPassword::class.java)
        startActivity(intent)

    }
}


