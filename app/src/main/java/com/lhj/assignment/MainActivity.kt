package com.lhj.assignment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.Fragment.FavoritesFragment
import com.lhj.assignment.Fragment.MainFragment
import com.lhj.assignment.Retrofit2.NetworkAPI
import com.lhj.assignment.Util.BaseActivity
import com.lhj.assignment.Util.MainViewModel
import com.lhj.assignment.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    lateinit var dbHelper: DBHelper

    override val layoutResourceId: Int get() = R.layout.activity_main   //resource init
    override val viewModel: MainViewModel by viewModel()    //koin

    override fun initStartView() {
        Log.e("Asdfgg", "initStartView");
    }

    override fun initDataBinding() {
        Log.e("Asdfgg", "initDataBinding");
        dbHelper = DBHelper(this, "main.db", null, 1)
//        viewModel.responseLiveData.observe(this, Observer {
//            Log.e("Asdfgg", "data : " + it.data.product.get(0).name)
//
//            val product = it.data?.product                                  //서버에서 받은 데이터
//
//            //서버에서 받은데이터와 DB의 데이터를 비교하여 중복체크 (중복되지 않은 것만 저장)
//
//            for (i in product?.indices!!) {
//                dbHelper.insertData(product[i])
//            }
//
//            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()  //메인 프래그먼트 설정
//        })
    }

    override fun initAfterBinding() {
        Log.e("Asdfgg", "initAfterBinding");
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchFragment(tab?.position)
            }
        })

        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()  //메인 프래그먼트 설정

//        test()

//        GlobalScope.launch(Dispatchers.Main) {
//            val job1 = async(Dispatchers.IO) {
//                viewModel.getResponseData("1.json")
//            }
//            val job2 = async(Dispatchers.IO) {
//                viewModel.getResponseData("2.json")
//            }
//            val job3 = async(Dispatchers.IO) {
//                viewModel.getResponseData("3.json")
//            }
//
////            job1.await()
////            job2.await()
////            job3.await()
//        }
    }

    fun test() = runBlocking<Unit> {
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

    private fun switchFragment(type: Int?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (type) {
            0 -> {
                transaction.replace(R.id.container, MainFragment())
            }
            1 -> {
                transaction.replace(R.id.container, FavoritesFragment())
            }
        }
//        transaction.addToBackStack(null)
        transaction.commit()
    }

//    fun searchTask() {
//
//        var gson = GsonBuilder().setLenient().create()
//
//        val retrofit = Retrofit.Builder().baseUrl("https://www.gccompany.co.kr/")
//            .addConverterFactory(GsonConverterFactory.create(gson)).build()
//
//        val api = retrofit.create(NetworkAPI::class.java)
//        val callgetSearchLocation =
//            api.getData("1.json")
//
//        callgetSearchLocation.enqueue(object : Callback<DataClass.ResponseData> {
//            override fun onResponse(
//                call: Call<DataClass.ResponseData>,
//                response: Response<DataClass.ResponseData>
//            ) {
//                Log.d("결과", "성공 : ${response.raw()}")
//                Log.d("결과", "성공 : ${response.body()}")
//                Log.d("결과", "성공 : ${response.body()?.data?.product?.get(0)?.name}")
//
//                val product = response.body()?.data?.product                    //서버에서 받은 데이터
//                var hotel: List<DataClass.MainData> = dbHelper.selectData()    //DB의 데이터
//
//                //서버에서 받은데이터와 DB의 데이터를 비교하여 중복체크 (중복되지 않은 것만 저장)
//                for (i in hotel?.indices!!) {
//                    for (j in product?.indices!!) {
//                        if (hotel[i].id.equals(product[j].id)) {
//                            Log.e("asdfgg", "중복입니다 : " + hotel[i].id);
//                        } else {
//                            dbHelper.insertData(product[i])
//                        }
////                    dbHelper.insertData(product[i])
//                    }
//                }
//
//            }
//
//
//            override fun onFailure(call: Call<DataClass.ResponseData>, t: Throwable) {
//                Log.e("결과:", "실패 : $t")
//            }
//        })
//    }
}