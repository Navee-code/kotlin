package com.example.myapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.apiinterface.ApiImageInterface
import com.example.myapplication.dataclass.MovieItem
import com.example.myapplication.instance.RetrofitImageInstance
import com.example.myapplication.adaptor.RvImageAdaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewModelRetrofitImage :ViewModel(){
    private lateinit  var data: MutableLiveData<List<MovieItem>>
    private lateinit var recyclerViewAdaptor: RvImageAdaptor


    init {
        data= MutableLiveData()
        recyclerViewAdaptor= RvImageAdaptor()
    }

    fun setAdaptor(arr:ArrayList<String>){
        recyclerViewAdaptor.setDataList(arr)
        recyclerViewAdaptor.notifyDataSetChanged()
    }
    fun getAdaptor(): RvImageAdaptor {
        return recyclerViewAdaptor
    }

    fun apiCall(){

        val retrofit = RetrofitImageInstance.getInstance().create(ApiImageInterface::class.java)
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
    fun getList(): MutableLiveData<List<MovieItem>> {

        return data
    }
}