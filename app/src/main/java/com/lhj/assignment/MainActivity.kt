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

    //TODO : 1. 리스트 페이징 2. 프레그먼트 이동 3. 즐겨찾기 정렬 (O) 4. 상세화면(갔다 온 뒤 리프레쉬) 5. 데이터 순차적으로 받기 6. UI 작업 7. 코드정리 및 주석

    lateinit var dbHelper: DBHelper

    override val layoutResourceId: Int get() = R.layout.activity_main   //resource init
    override val viewModel: MainViewModel by viewModel()    //koin

    override fun initStartView() {
        Log.e("Asdfgg", "initStartView");
    }

    override fun initDataBinding() {
        Log.e("Asdfgg", "initDataBinding");
        dbHelper = DBHelper(this, "main.db", null, 1)
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

        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment())
            .commit()  //메인 프래그먼트 설정
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
        transaction.commit()
    }
}