package com.lhj.assignment.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.lhj.assignment.Adapter.MainListAdapter
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.MainActivity
import com.lhj.assignment.Model.DataModel
import com.lhj.assignment.R
import com.lhj.assignment.Retrofit2.NetworkAPI
import com.lhj.assignment.Util.FavClick
import com.lhj.assignment.Util.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class MainFragment : Fragment(R.layout.fragment_main) {

    val viewModel: MainViewModel by viewModel()    //koin
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ASdfgg", "MainFragment....")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mainListAdapter = MainListAdapter(context)
        dbHelper = DBHelper(context, "main.db", null, 1)

        main_list.layoutManager = LinearLayoutManager(context)
        main_list.adapter = mainListAdapter

        mainListAdapter.setFavDataList(dbHelper.selectData())

        subscribeObservers(main_list.adapter as MainListAdapter)

        getApiData()

        mainListAdapter.setOnFavClickListener(object : FavClick {
            override fun onFavClick(v: View?, position: Int) {
                //즐겨찾기 존재 유무 확인
                var dbData = mainListAdapter.getFavDataList()
                Log.e("asdfgg", "dbData : " + dbData.size)
                if (dbData.size == 0) {
                    dbHelper.insertData(
                        DataClass.FavoriteData(
                            mainListAdapter.getData(position).id,
                            mainListAdapter.getData(position).name,
                            mainListAdapter.getData(position).rate,
                            mainListAdapter.getData(position).thumbnail,
                            LocalDateTime.now().toString().replace("T", " ")
                        )
                    )
                } else {
                    if (dbHelper.checkData(mainListAdapter.getData(position).id).size != 0) {
                        dbHelper.deleteFavData(mainListAdapter.getData(position).id)
                    } else {
                        dbHelper.insertData(
                            DataClass.FavoriteData(
                                mainListAdapter.getData(position).id,
                                mainListAdapter.getData(position).name,
                                mainListAdapter.getData(position).rate,
                                mainListAdapter.getData(position).thumbnail,
                                LocalDateTime.now().toString().replace("T", " ")
                            )
                        )
                    }

//                    for (i in dbData.indices) {
//                        Log.e("ASdfgg" , "dbData : " + dbData.get(i).id + "    mainListAdapter.getData(position).id : " + mainListAdapter.getData(position).id)
//                        if (dbData.get(i).id == mainListAdapter.getData(position).id) {
//                            //있음
//                            dbHelper.deleteFavData(mainListAdapter.getData(position).id)
//                        } else {
//                            dbHelper.insertData(
//                                DataClass.FavoriteData(
//                                    mainListAdapter.getData(position).id,
//                                    mainListAdapter.getData(position).name,
//                                    mainListAdapter.getData(position).rate,
//                                    mainListAdapter.getData(position).thumbnail,
//                                    LocalDateTime.now().toString().replace("T", " ")
//                                )
//                            )
//                        }
//                    }
                }

                mainListAdapter.setFavDataList(dbHelper.selectData())
            }
        })
    }

    private fun subscribeObservers(adapter: MainListAdapter) {
        viewModel.responseLiveData1.observe(this@MainFragment.viewLifecycleOwner, Observer {
            Log.e("Asdfgg", "data1 : " + it.data.product.get(0).name)
            adapter.setDataList(it.data?.product)
        })

        viewModel.responseLiveData2.observe(this@MainFragment.viewLifecycleOwner, Observer {
            Log.e("Asdfgg", "data2 : " + it.data.product.get(0).name)
            adapter.setDataList(it.data?.product)
        })

        viewModel.responseLiveData3.observe(this@MainFragment.viewLifecycleOwner, Observer {
            Log.e("Asdfgg", "data3 : " + it.data.product.get(0).name)
            adapter.setDataList(it.data?.product)
        })
    }

    fun getApiData() = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            Log.e("asdfgg", "순서 1")
            viewModel.getResponseData("1.json")
        }
        launch(Dispatchers.IO) {
            Log.e("asdfgg", "순서 2")
            delay(100)
            viewModel.getResponseData("2.json")
        }
        launch(Dispatchers.IO) {
            Log.e("asdfgg", "순서 3")
            delay(200)
            viewModel.getResponseData("3.json")
        }
    }
}