package com.lhj.assignment.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.R

class MainListAdapter() : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    private var mData: MutableList<DataClass.MainData> = mutableListOf()
    private var favData: MutableList<DataClass.FavoriteData> = mutableListOf()

    private lateinit var fav_click: Fav_Click

    interface Fav_Click {
        fun onFav_click(v: View?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position], position)
    }

    fun setDataList(dataList: List<DataClass.MainData>) {
//        mData = dataList as MutableList<DataClass.MainData>
        mData.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setFavDataList(dataList: List<DataClass.FavoriteData>) {
//        mData = dataList as MutableList<DataClass.MainData>
//        favData.addAll(dataList)
        favData = dataList as MutableList<DataClass.FavoriteData>
        notifyDataSetChanged()
    }

    fun addDataItem(data: DataClass.MainData) {
        mData.add(data)

    }

    fun setOnFavClickListener(listener: Fav_Click) {
        this.fav_click = listener
    }

    fun getData(pos: Int): DataClass.MainData {
        return mData.get(pos)
    }

    fun getFavData(pos: Int): DataClass.FavoriteData {
        return favData.get(pos)
    }

    fun getFavDataList(): List<DataClass.FavoriteData> {
        return favData
    }

//    fun setDataFav(pos : Int, isFav : String){
//        mData.get(pos).favorites = isFav
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val title_tv = itemView.findViewById<TextView>(R.id.title_tv)
        private val late_tv = itemView.findViewById<TextView>(R.id.late_tv)
        private val thumbnail_iv = itemView.findViewById<ImageView>(R.id.thumbnail_iv)
        private val fav_iv = itemView.findViewById<ImageView>(R.id.fav_iv)

        fun bind(mData: DataClass.MainData, pos: Int) {
            title_tv.text = mData.name
            late_tv.text = mData.rate.toString()
            Glide.with(itemView).load(mData.thumbnail).into(thumbnail_iv)

            for (i in favData.indices) {
                if (favData.get(i).id == mData.id) {
                    Log.e("Asdfg","변해라잉")
                    fav_iv.setImageResource(R.drawable.ic_star_on)
                    break
                } else {
                    fav_iv.setImageResource(R.drawable.ic_star_off)
                }
            }

//            if (mData.favorites.equals("Y")) {
//                fav_iv.setImageResource(R.drawable.ic_star_on)
//            } else if (mData.favorites.equals("N")) {
//                fav_iv.setImageResource(R.drawable.ic_star_off)
//            }
            fav_iv.setOnClickListener(View.OnClickListener { view: View? ->
                if (fav_click != null) {
                    fav_click.onFav_click(view, pos)
                }
            })
        }
    }
}