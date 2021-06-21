package com.lhj.assignment.Util

import android.view.View

interface FavClick {
    fun onFavClick(v: View?, position: Int)
}

interface SortClick {
    fun onSortClick(v: View?)
}