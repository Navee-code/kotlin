package com.example.myapplication.activity


import android.content.Intent
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adaptor.RvAdapter
import com.example.myapplication.data.UserDataBase
import com.example.myapplication.storage.SharedPrefer
import com.example.myapplication.viewmodels.ViewModelRetrofit
import com.example.myapplication.viewmodels.ViewModelRetrofitImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val data = ArrayList<String>()
    val imageData = ArrayList<String>()
    val share= SharedPrefer()
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar:ProgressBar
    private lateinit var viewModel: ViewModelRetrofit
    private lateinit var viewModel2: ViewModelRetrofitImage
    private lateinit var userDataBase:UserDataBase
    private lateinit var recyclerViewAdaptor: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         userDataBase= UserDataBase.getDatabase(this)


            progressBar = findViewById(R.id.progress_retrofit)
            viewModel2=ViewModelProvider(this).get(ViewModelRetrofitImage::class.java)
            viewModel = ViewModelProvider(this).get(ViewModelRetrofit::class.java)

            makeApiCallMovie()
            makeApiCall()
            getLoginName()


    }

    private fun makeApiCallMovie() {
        viewModel2.getList().observe(this, Observer {
            if (it != null) {
                for (item in it) {
                    imageData.add(item.download_url)
                }
            }

            
            viewModel2.setAdaptor(imageData)


        })
        viewModel2.apiCall()
        recyclerViewImage()
    }

    private fun recyclerViewImage() {
        var recycler_view = findViewById<RecyclerView>(R.id.recycler_view2)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        var  adapter=viewModel2.getAdaptor()
        recycler_view.adapter = adapter
    }


    private fun makeApiCall() {
        viewModel.getList().observe(this, Observer {
            if (it != null) {
                for (item in it) {
                    data.add(item.body)
                }
            }
            GlobalScope.launch(Dispatchers.IO){
                val tag=userDataBase.userDao().readAllData()
                withContext (Dispatchers.Main) {

                    viewModel.setAdaptor(data,tag)
                    progressBar.visibility = View.INVISIBLE
            }
            }

        })
        viewModel.apiCall()
        recyclerView()


    }


    private fun recyclerView() {
        var recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        var  adapter=viewModel.getAdaptor()
        recycler_view.adapter = adapter
    }

    private fun getLoginName(){
        val google= GoogleSignIn.getLastSignedInAccount(this)
        if(google!=null){
           var name= google.email.toString()
            Toast.makeText(this,"Welcome "+google.displayName,Toast.LENGTH_SHORT).show()
           title=name

        }

    }



    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
        super.onBackPressed()
    }

    fun navigate(view: View) {

        var intent=Intent(this, CreateTodoTask::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent=Intent(applicationContext, LoginActivity::class.java)
        val  gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc= GoogleSignIn.getClient(this,gso)
        val google= GoogleSignIn.getLastSignedInAccount(this)
        if(google!=null){

            gsc.signOut().addOnCompleteListener {
                finish()
                startActivity(intent)
            }
        }else{
            share.sharedSetBoolean(false,applicationContext)
            finish()
            Toast.makeText(this,"Sign out from real time",Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }






}




//
//GlobalScope.launch {
//    val retrofitBuilder = RetrofitInstance.getInstance().getData()
//    retrofitBuilder.enqueue(object : Callback<List<DataItem>> {
//        override fun onResponse(
//            call: Call<List<DataItem>>?,
//            response: Response<List<DataItem>>?
//        ) {
//
//            if (response?.body() != null) {
//                val reponse_body = response.body()
//                if (reponse_body != null) {
//                    for (myData in reponse_body) {
//                        data.add(myData.body)
//                    }
//                }
//            }
//
//            recyclerView()
//        }
//
//        override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
//            Log.e("ERROR", t.message.toString())
//        }
//
//    })
//}



//    private  fun getMyData() {
//        val retrofit =RetrofitInstance.getInstance().create(ApiInterface::class.java)
//
//        GlobalScope.launch (Dispatchers.IO){
//            val result=retrofit.getData()
//           if(result.body()!=null){
//               for (item in result.body()!!){
//                   data.add(item.body)
//               }
//               withContext (Dispatchers.Main) {
//                   progressBar.visibility = View.INVISIBLE
//                  recyclerView()
//               }
//           }
//        }
//    }






