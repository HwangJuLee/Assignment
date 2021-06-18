package com.lhj.assignment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.R

class MainListAdapter(private val context: Context, private val mData: List<DataClass.MainData>) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

//    private var data = mutableListOf<DataClass.MainData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val title_tv: TextView = itemView.findViewById(R.id.title_tv)
        private val late_tv: TextView = itemView.findViewById(R.id.late_tv)
        private val thumbnail_iv: ImageView = itemView.findViewById(R.id.thumbnail_iv)

        fun bind(mData: DataClass.MainData) {
            title_tv.text = mData.name
            late_tv.text = mData.rate.toString()
            Glide.with(itemView).load(mData.thumbnail).into(thumbnail_iv)

        }
    }
}