package com.lhj.assignment.Model

import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Retrofit2.NetworkAPI
import io.reactivex.Single
import retrofit2.Call

class DataModelImpl(private val service: NetworkAPI) : DataModel {

    override fun getData(apiType: String): Single<DataClass.ResponseData> {
        return service.getData(apiType)
    }
}