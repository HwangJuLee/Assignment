package com.lhj.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Database.DBHelper
import java.text.DecimalFormat
import java.time.LocalDateTime

class DetailActivity : AppCompatActivity() {

    private lateinit var thumbnail_iv: ImageView
    private lateinit var fav_iv: ImageView
    private lateinit var name_tv: TextView
    private lateinit var subject_tv: TextView
    private lateinit var price_tv: TextView
    private lateinit var rate_tv: TextView

    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var mData = intent.getSerializableExtra("mData") as DataClass.MainData
        dbHelper = DBHelper(this, "main.db", null, 1)
        var favData = dbHelper.checkData(mData.id)

        thumbnail_iv = findViewById(R.id.thumbnail_iv)
        fav_iv = findViewById(R.id.fav_iv)
        name_tv = findViewById(R.id.name_tv)
        subject_tv = findViewById(R.id.subject_tv)
        price_tv = findViewById(R.id.price_tv)
        rate_tv = findViewById(R.id.rate_tv)

        Glide.with(thumbnail_iv).load(mData.description.imagePath).into(thumbnail_iv)
        name_tv.text = mData.name
        subject_tv.text = mData.description.subject
        price_tv.text = DecimalFormat("#,##0").format(mData.description.price) + "원"
        rate_tv.text = mData.rate.toString()

        if (favData.size != 0) {
            fav_iv.setImageResource(R.drawable.ic_star_on)
        } else {
            fav_iv.setImageResource(R.drawable.ic_star_off)
        }

        fav_iv.setOnClickListener(View.OnClickListener { view: View? ->
            var dbData = dbHelper.selectData()
            if (dbData.size == 0) {
                dbHelper.insertData(
                    DataClass.FavoriteData(
                        mData.id,
                        mData.name,
                        mData.rate,
                        mData.thumbnail,
                        LocalDateTime.now().toString().replace("T", " ")
                    )
                )
                fav_iv.setImageResource(R.drawable.ic_star_on)
            } else {
                if (dbHelper.checkData(mData.id).size != 0) {
                    dbHelper.deleteFavData(mData.id)
                    fav_iv.setImageResource(R.drawable.ic_star_off)
                } else {
                    dbHelper.insertData(
                        DataClass.FavoriteData(
                            mData.id,
                            mData.name,
                            mData.rate,
                            mData.thumbnail,
                            LocalDateTime.now().toString().replace("T", " ")
                        )
                    )
                    fav_iv.setImageResource(R.drawable.ic_star_on)
                }
            }

        })

    }
}