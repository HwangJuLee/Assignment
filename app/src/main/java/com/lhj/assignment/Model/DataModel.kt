package com.lhj.assignment.Model

import com.lhj.assignment.Data.DataClass
import io.reactivex.Single
import retrofit2.Call

interface DataModel {
    fun getData(apiType: String): Single<DataClass.ResponseData>
}