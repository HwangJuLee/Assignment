package com.lhj.assignment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.lhj.assignment.Data.DataClass
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

        searchTask()
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

            }


            override fun onFailure(call: Call<DataClass.ResponseData>, t: Throwable) {
                Log.e("결과:", "실패 : $t")
            }
        })
    }
}