package com.lhj.assignment.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhj.assignment.Adapter.FavListAdapter
import com.lhj.assignment.Adapter.MainListAdapter
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.R
import com.lhj.assignment.Util.FavClick
import com.lhj.assignment.Util.SortClick
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDateTime

class FavoritesFragment : Fragment(R.layout.fragment_fav) {

    lateinit var dbHelper: DBHelper

//    var currentRegTimeType = DBHelper.DESC
//    var currentRatetype = DBHelper.DESC

    companion object {
        var currentRegTimeType = DBHelper.DESC
        var currentRatetype = DBHelper.DESC
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        var favListAdapter = FavListAdapter()
        dbHelper = DBHelper(context, "main.db", null, 1)

        fav_list.layoutManager = LinearLayoutManager(context)
        fav_list.adapter = favListAdapter

        favListAdapter.setFavDataList(dbHelper.selectData("regTime", currentRegTimeType))

        favListAdapter.setOnFavClickListener(object : FavClick {
            override fun onFavClick(v: View?, position: Int) {
                //즐겨찾기 존재 유무 확인
                dbHelper.deleteFavData(favListAdapter.getFavData(position).id)
                favListAdapter.removeFavData(position)
            }

        })

        favListAdapter.setOnRegTimeSortClickListener(object : SortClick{
            override fun onSortClick(v: View?) {
                Log.e("asdfgg" , "호잉?");
                if (currentRegTimeType.equals(DBHelper.DESC)){
                    favListAdapter.setFavDataList(dbHelper.selectData("regTime", DBHelper.ASC))
                    currentRegTimeType = DBHelper.ASC
                } else if (currentRegTimeType.equals(DBHelper.ASC)){
                    favListAdapter.setFavDataList(dbHelper.selectData("regTime", DBHelper.DESC))
                    currentRegTimeType = DBHelper.DESC
                }
            }
        })

        favListAdapter.setOnRateSortClickListener(object : SortClick{
            override fun onSortClick(v: View?) {
                Log.e("asdfgg" , "호잉?222222");
                if (currentRatetype.equals(DBHelper.DESC)){
                    favListAdapter.setFavDataList(dbHelper.selectData("rate", DBHelper.ASC))
                    currentRatetype = DBHelper.ASC
                } else if (currentRatetype.equals(DBHelper.ASC)){
                    favListAdapter.setFavDataList(dbHelper.selectData("rate", DBHelper.DESC))
                    currentRatetype = DBHelper.DESC
                }
            }
        })
    }
}