package com.lhj.assignment.Retrofit2

import com.lhj.assignment.Data.DataClass
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkAPI {
    //데이터 가져오기
    @GET("App/json/{apiType}")
    fun getData(
        @Path("apiType") apiType : String
    ): Single<DataClass.ResponseData>

}