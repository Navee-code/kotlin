package com.example.myapplication.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.apiinterface.ApiInterface
import com.example.myapplication.dataclass.DataItem
import com.example.myapplication.instance.RetrofitInstance
import com.example.myapplication.adaptor.RvAdapter
import com.example.myapplication.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ViewModelRetrofit : ViewModel() {

     private lateinit  var data:MutableLiveData<List<DataItem>>
    private lateinit var recyclerViewAdaptor: RvAdapter


init {

    data= MutableLiveData()
    recyclerViewAdaptor= RvAdapter()

}

    fun setAdaptor(arr: ArrayList<String>, tag: List<UserData>){
        recyclerViewAdaptor.setDataList(arr,tag)
        recyclerViewAdaptor.notifyDataSetChanged()
    }
    fun getAdaptor(): RvAdapter {
        return recyclerViewAdaptor
    }
    fun deleteFromApi(delete:String){
        val retrofit = RetrofitInstance.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {



            }catch (e:Exception){
                Log.e("TAG",e.message.toString())
                //Toast.makeText(contextActivity,"No internet",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun apiCall(){
        val retrofit = RetrofitInstance.getInstance().create(ApiInterface::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                try {

                    val result = retrofit.getData()
                    if (result.body() != null) {

                        data.postValue(result.body())

                    }
                }catch (e:Exception){
                    Log.e("TAG",e.message.toString())
                    //Toast.makeText(contextActivity,"No internet",Toast.LENGTH_SHORT).show()
                }
            }

            }

    fun getList():MutableLiveData<List<DataItem>>{

        return data
    }
}