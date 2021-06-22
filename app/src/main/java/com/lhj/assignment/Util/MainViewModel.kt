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
    val responseLiveData: LiveData<DataClass.ResponseData> get() = _responseLiveData

    fun getResponseData(apiType: String) {
        addDisposable(model.getData(apiType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _responseLiveData.value = this
                }
            }, {
                Log.e("LHJ", "response error, message : ${it.message}")
            })
        )
    }
}