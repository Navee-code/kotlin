package com.example.myapplication.adaptor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.BodyMessage
import com.example.myapplication.R
import com.example.myapplication.viewmodels.ViewModelRetrofit

class RvAdapter :RecyclerView.Adapter<RvAdapter.ViewHolder>(){



    var list1=ArrayList<String>()


    fun setDataList(list2:ArrayList<String>){
        for (item in list2){
            list1.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

            .inflate(R.layout.card_view_adaptor, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.text1.text=list1.get(position)

                    holder.itemView.setOnClickListener {
                        var intent=Intent(holder.context, BodyMessage::class.java)
                        intent.putExtra("Body",list1.get(position))
                        holder.context.startActivity(intent)
//                    ViewModelRetrofit().deleteFromApi(list1.get(position))
                    }
      setAnimation(holder)

    }
    private fun setAnimation(holder: ViewHolder) {
        val animation = AnimationUtils.loadAnimation(holder.context, android.R.anim.slide_in_left)
        holder.itemView.startAnimation(animation)
    }
    override fun getItemCount(): Int {

       return list1.size
    }
    inner class ViewHolder(itemVIew: View):RecyclerView.ViewHolder(itemVIew){
         var text1= itemVIew.findViewById<TextView>(R.id.todo_text)
        var context= itemVIew.context

        //var card_animation=itemVIew.findViewById<CardView>(R.id.recycler_card_view)
    }
}


