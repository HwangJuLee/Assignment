package com.lhj.assignment.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.DetailActivity
import com.lhj.assignment.R
import com.lhj.assignment.Util.FavClick

class MainListAdapter(val context: Activity?) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    private var mData: MutableList<DataClass.MainData> = mutableListOf()
    private var favData: MutableList<DataClass.MainData> = mutableListOf()

    private lateinit var favClick: FavClick


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
        mData.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setFavDataList(dataList: List<DataClass.MainData>) {
        favData = dataList as MutableList<DataClass.MainData>
    }

    fun getData(pos: Int): DataClass.MainData {
        return mData.get(pos)
    }

    fun getFavDataList(): List<DataClass.MainData> {
        return favData
    }

    fun setOnFavClickListener(listener: FavClick) {
        this.favClick = listener
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val main = itemView.findViewById<ConstraintLayout>(R.id.main)
        private val name_tv = itemView.findViewById<TextView>(R.id.name_tv)
        private val rate_tv = itemView.findViewById<TextView>(R.id.rate_tv)
        private val thumbnail_iv = itemView.findViewById<ImageView>(R.id.thumbnail_iv)
        private val fav_iv = itemView.findViewById<ImageView>(R.id.fav_iv)

        fun bind(mData: DataClass.MainData, pos: Int) {
            name_tv.text = mData.name
            rate_tv.text = mData.rate.toString()
            Glide.with(itemView).load(mData.thumbnail).into(thumbnail_iv)
            changeFavImage(mData.id)

            //상세보기 클릭
            main.setOnClickListener { view: View? ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("mData", mData);
                context?.startActivityForResult(intent, 1000)
            }

            //즐겨찾기 클릭
            fav_iv.setOnClickListener(View.OnClickListener { view: View? ->
                if (favClick != null) {
                    favClick.onFavClick(view, pos)
                    changeFavImage(mData.id)
                }
            })
        }

        //즐겨찾기 이미지 변경
        private fun changeFavImage(id: Int) {
            for (i in favData.indices) {
                if (favData.get(i).id == id) {
                    fav_iv.setImageResource(R.drawable.ic_star_on)
                    break
                } else {
                    fav_iv.setImageResource(R.drawable.ic_star_off)
                }
            }
        }
    }
}