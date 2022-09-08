package com.example.myapplication.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R

class RvImageAdaptor :RecyclerView.Adapter<RvImageAdaptor.ImageViewHolder>(){
    var list2=ArrayList<String>()

    fun setDataList(list:ArrayList<String>){
        for (item in list){
            list2.add(item)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_adaptor_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.progressBar.visibility=View.INVISIBLE
    Glide.with(holder.context)
        .load(list2.get(position))
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.kotlin_splash)
        .into(holder.image)

    }

    override fun getItemCount(): Int {

        return list2.size
    }
    inner class ImageViewHolder(itemVIew: View): RecyclerView.ViewHolder(itemVIew){
        var image= itemVIew.findViewById<ImageView>(R.id.image)
        var context= itemVIew.context
        var progressBar=itemVIew.findViewById<ProgressBar>(R.id.progressBar)
    }
}