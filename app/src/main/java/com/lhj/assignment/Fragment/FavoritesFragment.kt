package com.lhj.assignment.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhj.assignment.Adapter.FavListAdapter
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.MainActivity
import com.lhj.assignment.R
import com.lhj.assignment.Util.FavClick
import com.lhj.assignment.Util.SortClick
import kotlinx.android.synthetic.main.fragment_fav.*

class FavoritesFragment() : Fragment(R.layout.fragment_fav) {

    var dbHelper: DBHelper = MainActivity.dbHelper

    companion object {
        var currentRegTimeType = DBHelper.DESC
        var currentRatetype = DBHelper.DESC
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var favListAdapter = FavListAdapter(activity)

        fav_list.layoutManager = LinearLayoutManager(context)
        fav_list.adapter = favListAdapter

        favListAdapter.setDataList(dbHelper.selectData("regTime", currentRegTimeType))

        checkEmptyFav(favListAdapter.itemCount)

        favListAdapter.setOnFavClickListener(object : FavClick {
            override fun onFavClick(v: View?, position: Int) {
                //즐겨찾기 존재 유무 확인
                dbHelper.deleteFavData(favListAdapter.getFavData(position).id)
                favListAdapter.removeFavData(position)

                checkEmptyFav(favListAdapter.itemCount)
            }

        })

        favListAdapter.setOnRegTimeSortClickListener(object : SortClick {
            override fun onSortClick(v: View?) {
                if (currentRegTimeType.equals(DBHelper.DESC)) {
                    favListAdapter.setDataList(dbHelper.selectData("regTime", DBHelper.ASC))
                    currentRegTimeType = DBHelper.ASC
                } else if (currentRegTimeType.equals(DBHelper.ASC)) {
                    favListAdapter.setDataList(dbHelper.selectData("regTime", DBHelper.DESC))
                    currentRegTimeType = DBHelper.DESC
                }
            }
        })

        favListAdapter.setOnRateSortClickListener(object : SortClick {
            override fun onSortClick(v: View?) {
                if (currentRatetype.equals(DBHelper.DESC)) {
                    favListAdapter.setDataList(dbHelper.selectData("rate", DBHelper.ASC))
                    currentRatetype = DBHelper.ASC
                } else if (currentRatetype.equals(DBHelper.ASC)) {
                    favListAdapter.setDataList(dbHelper.selectData("rate", DBHelper.DESC))
                    currentRatetype = DBHelper.DESC
                }
            }
        })
    }

    fun checkEmptyFav(count : Int){
        if (count == 1){
            noFav_tv.visibility = View.VISIBLE
            fav_list.visibility = View.GONE
        } else{
            noFav_tv.visibility = View.GONE
            fav_list.visibility = View.VISIBLE
        }
    }
}