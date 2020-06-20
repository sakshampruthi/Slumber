package com.thesleepconsultingcompany.slumber.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thesleepconsultingcompany.slumber.R
import com.thesleepconsultingcompany.slumber.ui.HeadacheActivity
import com.thesleepconsultingcompany.slumber.ui.SleepActivity

class DiaryAdapter(val context: Context, val list: ArrayList<String>) : RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    val images = arrayOf(
        R.drawable.sleep,
        R.drawable.headache
    )
    val act = arrayOf(SleepActivity::class.java, HeadacheActivity::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list.get(position)
        holder.imageView.setBackgroundResource(images[position])
        holder.itv.setOnClickListener {
            context.startActivity(Intent(context, act[position]))
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.cardtitle)
        val imageView = itemView.findViewById<ImageView>(R.id.card_image)
        val itv = itemView
    }
}