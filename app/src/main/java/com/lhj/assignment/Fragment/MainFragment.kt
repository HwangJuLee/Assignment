package com.lhj.assignment.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.lhj.assignment.Adapter.MainListAdapter
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.R
import com.lhj.assignment.Retrofit2.NetworkAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

    lateinit var mData: List<DataClass.MainData>
    lateinit var mainList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        searchTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        mainList = view.findViewById<RecyclerView>(R.id.main_list)
        mainList.layoutManager = LinearLayoutManager(context)
//        mainList.adapter = MainListAdapter(requireContext(), mData)

        return view
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

                mData = response.body()?.data?.product!!

                mainList.adapter = MainListAdapter(requireContext(), mData)
            }


            override fun onFailure(call: Call<DataClass.ResponseData>, t: Throwable) {
                Log.e("결과:", "실패 : $t")
            }
        })
    }
}