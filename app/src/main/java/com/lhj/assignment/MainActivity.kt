package com.lhj.assignment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.lhj.assignment.Database.DBHelper
import com.lhj.assignment.Fragment.FavoritesFragment
import com.lhj.assignment.Fragment.MainFragment
import com.lhj.assignment.Util.BaseActivity
import com.lhj.assignment.Util.MainViewModel
import com.lhj.assignment.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        lateinit var dbHelper: DBHelper
    }

    override val layoutResourceId: Int get() = R.layout.activity_main   //resource init
    override val viewModel: MainViewModel by viewModel()
    var time : Long = 0

    override fun initStartView() {
        Log.e(TAG, "initStartView")
    }

    override fun initDataBinding() {
        Log.e(TAG, "initDataBinding")
        //DB init
        dbHelper = DBHelper(this, "main.db", null, 1)
    }

    override fun initAfterBinding() {
        Log.e(TAG, "initAfterBinding")

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment()).commit()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchFragment(tab?.position)
            }
        })
    }

    //메인 & 즐겨찾기 변겅
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

    //상세보기 -> 메인&즐겨찾기 화면 refresh
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment()).commit()
            }
        } else if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FavoritesFragment()).commit()
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis()
            Toast.makeText(applicationContext, getString(R.string.app_exit), Toast.LENGTH_SHORT)
                .show()
        } else if (System.currentTimeMillis() - time < 2000) {
            finish()
        }
    }
}