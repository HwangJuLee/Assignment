package com.lhj.assignment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Fragment.FavoritesFragment
import com.lhj.assignment.Fragment.MainFragment
import com.lhj.assignment.Retrofit2.NetworkAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO 데이터 받기 (코루틴) DB에 넣어서 관리

        var tabLayout : TabLayout = findViewById(R.id.tabs)

        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchFragment(tab?.position)
            }
        })

        searchTask()
    }

    private fun switchFragment(type : Int?) {
        val transaction = supportFragmentManager.beginTransaction()
        when(type){
            0 -> {
                transaction.replace(R.id.container, MainFragment())
            }
            1 -> {
                transaction.replace(R.id.container, FavoritesFragment())
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun searchTask() {

        var gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder().baseUrl("https://www.gccompany.co.kr/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

        val api = retrofit.create(NetworkAPI::class.java)
        val callgetSearchLocation =
            api.getData("1.json")

        callgetSearchLocation.enqueue(object : Callback<DataClass.ResponseData> {
            override fun onResponse(
                call: Call<DataClass.ResponseData>,
                response: Response<DataClass.ResponseData>
            ) {
                Log.d("결과", "성공 : ${response.raw()}")
                Log.d("결과", "성공 : ${response.body()}")
                Log.d("결과", "성공 : ${response.body()?.data?.product?.get(0)?.name}")

            }


            override fun onFailure(call: Call<DataClass.ResponseData>, t: Throwable) {
                Log.e("결과:", "실패 : $t")
            }
        })
    }
}