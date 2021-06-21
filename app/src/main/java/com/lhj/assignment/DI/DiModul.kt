package com.lhj.assignment.DI

import com.lhj.assignment.Adapter.MainListAdapter
import com.lhj.assignment.Model.DataModel
import com.lhj.assignment.Model.DataModelImpl
import com.lhj.assignment.Retrofit2.NetworkAPI
import com.lhj.assignment.Util.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

var retrofitPart = module {
    single<NetworkAPI> {
        Retrofit.Builder()
            .baseUrl("https://www.gccompany.co.kr/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkAPI::class.java)
    }
}

var adapterPart = module {
    factory {
        MainListAdapter(null)
    }
}

var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }
}

var mainDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart)