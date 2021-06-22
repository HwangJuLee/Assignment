package com.lhj.assignment.Util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.MainActivity
import com.lhj.assignment.Model.DataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val model: DataModel) : BaseViewModel() {
    private val _responseLiveData = MutableLiveData<DataClass.ResponseData>()
    private val _responseLiveData1 = MutableLiveData<DataClass.ResponseData>()
    private val _responseLiveData2 = MutableLiveData<DataClass.ResponseData>()
    private val _responseLiveData3 = MutableLiveData<DataClass.ResponseData>()

    val responseLiveData: LiveData<DataClass.ResponseData> get() = _responseLiveData
    val responseLiveData1: LiveData<DataClass.ResponseData> get() = _responseLiveData1
    val responseLiveData2: LiveData<DataClass.ResponseData> get() = _responseLiveData2
    val responseLiveData3: LiveData<DataClass.ResponseData> get() = _responseLiveData3

    fun getResponseData(apiType: String) {
        addDisposable(model.getData(apiType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _responseLiveData.value = this
//                    if (apiType.equals(MainActivity.API_1)) {
//                        _responseLiveData1.value = this
//                    } else if (apiType.equals(MainActivity.API_2)) {
//                        _responseLiveData2.value = this
//                    } else if (apiType.equals(MainActivity.API_3)) {
//                        _responseLiveData3.value = this
//                    }
                }
            }, {
                Log.e("LHJ", "response error, message : ${it.message}")
            })
        )
    }
}