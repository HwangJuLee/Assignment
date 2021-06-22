package com.lhj.assignment.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhj.assignment.Adapter.MainListAdapter
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.MainActivity
import com.lhj.assignment.R
import com.lhj.assignment.Util.FavClick
import com.lhj.assignment.Util.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class MainFragment() : Fragment(R.layout.fragment_main) {

    var dbHelper: DBHelper = MainActivity.dbHelper
    lateinit var mainListAdapter: MainListAdapter

    val viewModel: MainViewModel by viewModel()
    var checkPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainListAdapter = MainListAdapter(activity)

        main_list.layoutManager = LinearLayoutManager(context)
        main_list.adapter = mainListAdapter
        main_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalCount = recyclerView.adapter!!.itemCount

                if (lastPosition + 1 == totalCount) {
                    if(totalCount % 20 == 0){
                        checkPage++
                        getApiData(checkPage)
                    }
                }
            }
        })

        subscribeObservers()
        getApiData(checkPage)

        mainListAdapter.setOnFavClickListener(object : FavClick {
            override fun onFavClick(v: View?, position: Int) {
                //즐겨찾기 존재 유무 확인
                var dbData = mainListAdapter.getFavDataList()
                if (dbData.size == 0) {
                    dbHelper.insertData(
                        DataClass.MainData(
                            mainListAdapter.getData(position).id,
                            mainListAdapter.getData(position).name,
                            mainListAdapter.getData(position).rate,
                            mainListAdapter.getData(position).thumbnail,
                            mainListAdapter.getData(position).description,
                            LocalDateTime.now().toString().replace("T", " ")
                        )
                    )
                } else {
                    if (dbHelper.checkData(mainListAdapter.getData(position).id).size != 0) {
                        dbHelper.deleteFavData(mainListAdapter.getData(position).id)
                    } else {
                        dbHelper.insertData(
                            DataClass.MainData(
                                mainListAdapter.getData(position).id,
                                mainListAdapter.getData(position).name,
                                mainListAdapter.getData(position).rate,
                                mainListAdapter.getData(position).thumbnail,
                                mainListAdapter.getData(position).description,
                                LocalDateTime.now().toString().replace("T", " ")
                            )
                        )
                    }
                }

                mainListAdapter.setFavDataList(dbHelper.selectData())
            }
        })
    }

    private fun subscribeObservers() {
        viewModel.responseLiveData.observe(this@MainFragment.viewLifecycleOwner, Observer {
//            mData.addAll(it.data?.product)
            mainListAdapter.setDataList(it.data?.product)
            mainListAdapter.setFavDataList(dbHelper.selectData())
        })

//        viewModel.responseLiveData2.observe(this@MainFragment.viewLifecycleOwner, Observer {
//            mData.addAll(it.data?.product)
//        })
//
//        viewModel.responseLiveData3.observe(this@MainFragment.viewLifecycleOwner, Observer {
//            mData.addAll(it.data?.product)
//            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment(mData))
//                .commit()
//        })
    }

    //API 호출
    fun getApiData(apiType : Int) = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            viewModel.getResponseData(apiType.toString() + ".json")
//            delay(300)
//            viewModel.getResponseData(MainActivity.API_2)
//            delay(300)
//            viewModel.getResponseData(MainActivity.API_3)
        }
    }

}