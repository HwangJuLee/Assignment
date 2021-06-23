package com.lhj.assignment.Adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.DetailActivity
import com.lhj.assignment.Fragment.FavoritesFragment
import com.lhj.assignment.R
import com.lhj.assignment.Util.FavClick
import com.lhj.assignment.Util.SortClick

class FavListAdapter(val context: Activity?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    private var mData: MutableList<DataClass.MainData> = mutableListOf()

    private lateinit var favClick: FavClick
    private lateinit var regTimeSortClick: SortClick
    private lateinit var rateSortClick: SortClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.e("LHJ", "viewType : " + viewType)
        if (viewType == TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent!!.context).inflate(R.layout.fav_list_item, parent, false)
            return MainViewHolder(view)
        } else {
            val headView: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.head_fav_list_item, parent, false)
            return HeadViewHolder(headView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            holder.bind(mData[position - 1], position - 1)
        } else if (holder is HeadViewHolder) {
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position)) {
            return TYPE_HEADER
        } else {
            return TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }

    fun setDataList(dataList: List<DataClass.MainData>) {
        mData = dataList as MutableList<DataClass.MainData>
        notifyDataSetChanged()
    }

    fun getFavData(pos: Int): DataClass.MainData {
        return mData.get(pos)
    }

    fun removeFavData(pos: Int) {
        mData.removeAt(pos)
        notifyDataSetChanged()
    }

    fun setOnFavClickListener(listener: FavClick) {
        this.favClick = listener
    }

    fun setOnRegTimeSortClickListener(listener: SortClick) {
        this.regTimeSortClick = listener
    }

    fun setOnRateSortClickListener(listener: SortClick) {
        this.rateSortClick = listener
    }

    fun isPositionHeader(position: Int): Boolean {
        return position == TYPE_HEADER
    }

    inner class MainViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val main = itemView.findViewById<ConstraintLayout>(R.id.main)
        private val name_tv = itemView.findViewById<TextView>(R.id.name_tv)
        private val rate_tv = itemView.findViewById<TextView>(R.id.rate_tv)
        private val date_tv = itemView.findViewById<TextView>(R.id.date_tv)
        private val thumbnail_iv = itemView.findViewById<ImageView>(R.id.thumbnail_iv)
        private val fav_iv = itemView.findViewById<ImageView>(R.id.fav_iv)

        fun bind(fData: DataClass.MainData, pos: Int) {
            name_tv.text = fData.name
            rate_tv.text = fData.rate.toString()
            date_tv.text = "등록일자 : " + fData.regTime
            Glide.with(itemView).load(fData.thumbnail).into(thumbnail_iv)
            changeFavImage(fData.id)

            main.setOnClickListener { view: View? ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("mData", fData);
                context?.startActivityForResult(intent, 1001)
            }

            fav_iv.setOnClickListener(View.OnClickListener { view: View? ->
                if (favClick != null) {
                    favClick.onFavClick(view, pos)
                    changeFavImage(fData.id)
                }
            })
        }

        private fun changeFavImage(id: Int) {
            for (i in mData.indices) {
                if (mData.get(i).id == id) {
                    fav_iv.setImageResource(R.drawable.ic_star_on)
                    break
                } else {
                    fav_iv.setImageResource(R.drawable.ic_star_off)
                }
            }
        }
    }

    inner class HeadViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val regTimeLayout = itemView.findViewById<LinearLayout>(R.id.regTime_sort_layout)
        private val rateLayout = itemView.findViewById<LinearLayout>(R.id.rate_sort_layout)
        private val regTime_sort_tv = itemView.findViewById<TextView>(R.id.regTime_sort_tv)
        private val rate_sort_tv = itemView.findViewById<TextView>(R.id.rate_sort_tv)
        private val regTime_iv = itemView.findViewById<ImageView>(R.id.regTime_sort_iv)
        private val rate_iv = itemView.findViewById<ImageView>(R.id.rate_sort_iv)

        fun bind() {

            regTimeLayout.setOnClickListener { view: View? ->
                if (regTimeSortClick != null) {
                    regTimeSortClick.onSortClick(view)
                    changeImage(regTime_iv, FavoritesFragment.currentRegTimeType, 1)
                }
            }

            rateLayout.setOnClickListener { view: View? ->
                if (rateSortClick != null) {
                    rateSortClick.onSortClick(view)
                    changeImage(rate_iv, FavoritesFragment.currentRatetype, 2)
                }
            }
        }

        fun changeImage(sort_iv: ImageView, sortType: String, checkType: Int) {
            if (checkType == 1) {
                regTime_sort_tv.setTextColor(Color.BLUE)
                rate_sort_tv.setTextColor(Color.parseColor("#333333"))
            } else if (checkType == 2) {
                rate_sort_tv.setTextColor(Color.BLUE)
                regTime_sort_tv.setTextColor(Color.parseColor("#333333"))
            }

            if (sortType.equals(DBHelper.ASC)) {
                sort_iv.setImageResource(R.drawable.ic_up)
            } else if (sortType.equals(DBHelper.DESC)) {
                sort_iv.setImageResource(R.drawable.ic_down)
            }
        }
    }
}