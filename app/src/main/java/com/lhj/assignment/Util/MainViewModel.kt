package com.lhj.assignment.Util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lhj.assignment.Data.DataClass
import com.lhj.assignment.Model.DataModel

class MainViewModel (private val model:DataModel): BaseViewModel() {
    private val _imageSearchResponseLiveData = MutableLiveData<DataClass.ResponseData>()
    val imageSearchResponseLiveData: LiveData<DataClass.ResponseData>
        get() = _imageSearchResponseLiveData
}